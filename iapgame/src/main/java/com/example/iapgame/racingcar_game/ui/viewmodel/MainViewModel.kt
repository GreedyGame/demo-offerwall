package com.example.iapgame.racingcar_game.ui.viewmodel

import androidx.compose.ui.geometry.Rect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iapgame.R
import com.example.iapgame.racingcar_game.domain.usecase.GetCarsUseCase
import com.example.iapgame.racingcar_game.domain.usecase.GetCoinsUseCase
import com.example.iapgame.racingcar_game.domain.usecase.GetHighscoreUseCase
import com.example.iapgame.racingcar_game.domain.usecase.SaveCoinsUseCase
import com.example.iapgame.racingcar_game.domain.usecase.SaveHighscoreUseCase
import com.example.iapgame.racingcar_game.domain.usecase.UpdateCarsUseCase
import com.example.iapgame.racingcar_game.ui.models.AccelerationData
import com.example.iapgame.racingcar_game.ui.models.MovementInput
import com.example.iapgame.racingcar_game.ui.models.NightRacingResourcePack
import com.example.iapgame.racingcar_game.ui.models.RacingResourcePack
import com.example.iapgame.racingcar_game.ui.shop.CarInfo
import com.example.iapgame.racingcar_game.utils.Constants.DEFAULT_ACCELEROMETER_SENSITIVITY
import com.example.iapgame.racingcar_game.utils.Constants.INITIAL_GAME_SCORE
import com.example.iapgame.racingcar_game.utils.SoundRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel constructor(
    private val getHighscoreUseCase: GetHighscoreUseCase,
    private val saveHighscoreUseCase: SaveHighscoreUseCase,
    private val getCoinsUseCase: GetCoinsUseCase,
    private val saveCoinsUseCase: SaveCoinsUseCase,
    private val getCarsUseCase: GetCarsUseCase,
    private val updateCarsUseCase: UpdateCarsUseCase,
    private val soundRepository: SoundRepository,
) : ViewModel() {

    private val _acceleration = MutableStateFlow(AccelerationData(0f, 0f, 0f))
    val acceleration = _acceleration.asStateFlow()

    private val _movementInput = MutableStateFlow(MovementInput.TapGestures)
    val movementInput = _movementInput.asStateFlow()

    private val _availableCoins = MutableStateFlow(0)
    val availableCoins = _availableCoins.asStateFlow()

    private val _gameScore = MutableStateFlow(INITIAL_GAME_SCORE)
    val gameScore = _gameScore.asStateFlow()

    private val _finalScore = MutableStateFlow(INITIAL_GAME_SCORE)
    val finalScore = _finalScore.asStateFlow()

    private val _highscore = MutableStateFlow(0)
    val highscore = _highscore.asStateFlow()

    private val _cars = MutableStateFlow(listOf<CarInfo>())
    val cars = _cars.asStateFlow()

    private val _isCarCollided = MutableStateFlow(false)
    val isCarCollided = _isCarCollided.asStateFlow()

    private val _resourcePack = MutableStateFlow<RacingResourcePack>(NightRacingResourcePack())
    val resourcePack = _resourcePack.asStateFlow()

    val vibrateSharedFlow = MutableSharedFlow<Unit>(replay = 1)

    private val carRectStateFlow = MutableStateFlow<Rect?>(null)
    private val blockerRectsStateFlow = MutableStateFlow<List<Rect>>(emptyList())

    private val carAndBlockerCollisionStateFlow =
        combine(carRectStateFlow.filterNotNull(), blockerRectsStateFlow) { carRect, blockerRects ->
            checkBlockerAndCarCollision(blockerRects, carRect)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    init {
        observeCollision()
        observeHighscore()
        observeAvailableCoins()
        observeShopCars()
        observeSelectedCar()

        soundRepository.loadSound(NEW_HIGHSCORE_SOUND_ID, R.raw.new_highscore)
        soundRepository.loadSound(BLOCKER_HIT_SOUND_ID, R.raw.blocker_hit)
        soundRepository.loadSound(MILESTONE_REACH_SOUND_ID, R.raw.milestone_reach)
    }

    private fun observeSelectedCar() {
        viewModelScope.launch {
            _resourcePack.emit(NightRacingResourcePack().copy(carImageDrawable = getCarsUseCase.selectedCar().carImage))
        }
    }

    private fun observeShopCars() {
        viewModelScope.launch {
            _cars.emit(getCarsUseCase.execute())
        }
    }

    private fun observeHighscore() {
        getHighscoreUseCase.execute().onEach {
            _highscore.value = it
        }.launchIn(viewModelScope)
    }

    private fun observeAvailableCoins() {
        getCoinsUseCase.execute().onEach {
            _availableCoins.value = it
        }.launchIn(viewModelScope)
    }

    private fun observeCollision() {
        carAndBlockerCollisionStateFlow.onEach { hasCollision ->
            _isCarCollided.update { hasCollision }
            if (hasCollision) {
                _finalScore.update { _gameScore.value }
                resetGameScore()
//                _gameScore.update { currentScore ->
//                    val newScore = currentScore - COLLISION_SCORE_PENALTY
//                    newScore.takeIf { it > INITIAL_GAME_SCORE } ?: INITIAL_GAME_SCORE
//                }
                playBlockerHitSound()
                vibrateSharedFlow.tryEmit(Unit)
            }
        }.launchIn(viewModelScope)
    }

    fun setAcceleration(
        accelerationX: Float,
        accelerationY: Float,
        accelerationZ: Float,
        sensitivity: Int = DEFAULT_ACCELEROMETER_SENSITIVITY
    ) {
        _acceleration.update {
            it.copy(
                x = accelerationX * sensitivity,
                y = accelerationY * sensitivity,
                z = accelerationZ * sensitivity
            )
        }
    }

    fun setMovementInput(movementInput: MovementInput) {
        _movementInput.update { movementInput }
    }

    fun increaseGameScore() {
        _gameScore.update { currentScore ->
            (currentScore + 1).also { newScore ->
                saveNewHighscore(newScore)
                if (newScore % 10 == 0) playMilestoneReachSound()
            }
        }
    }

    private fun saveNewHighscore(newScore: Int) {
        viewModelScope.launch {
            saveHighscoreUseCase.execute(newScore)
        }
    }

    private fun resetGameScore() {
        _gameScore.update { INITIAL_GAME_SCORE }
    }

    private fun playBlockerHitSound() {
        soundRepository.playSound(BLOCKER_HIT_SOUND_ID)
    }

    fun playMilestoneReachSound() {
        soundRepository.playSound(MILESTONE_REACH_SOUND_ID)
    }


    fun playBackgroundMusic() {
        soundRepository.playBackgroundMusic()
    }

    fun stopBackgroundMusic() {
        soundRepository.stopBackgroundMusic()
    }

    fun releaseSounds() {
        soundRepository.release()
    }

    fun updateCarRect(carRect: Rect) {
        carRectStateFlow.value = carRect
    }

    fun newCarSelected(carInfo: CarInfo) {
        viewModelScope.launch {
            updateCarsUseCase.changeSelectedCar(carInfo)
            observeShopCars()
            observeSelectedCar()
        }
    }

    fun updateBlockerRects(blockerRects: List<Rect>) {
        blockerRectsStateFlow.value = blockerRects
    }

    companion object {
        const val NEW_HIGHSCORE_SOUND_ID = 1
        const val BLOCKER_HIT_SOUND_ID = 2
        const val MILESTONE_REACH_SOUND_ID = 3
    }

    private fun checkBlockerAndCarCollision(blockerRects: List<Rect>, carRect: Rect): Boolean {
        return blockerRects.any { blockerRect ->
            blockerRect.overlaps(carRect)
        }
    }

    fun buyCar(carInfo: CarInfo) {
        viewModelScope.launch {
            saveCoinsUseCase.debit(carInfo.carCost)
            updateCarsUseCase.buyCar(carInfo)
            newCarSelected(carInfo)
        }
    }

    fun creditCoins(coins: Int) {
        viewModelScope.launch {
            saveCoinsUseCase.credit(coins)
        }
    }
}