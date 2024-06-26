package com.example.iapgame.racingcar_game.ui.game

import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import com.example.iapgame.racingcar_game.ui.game.state.BackgroundState
import com.example.iapgame.racingcar_game.ui.game.state.BlockersState
import com.example.iapgame.racingcar_game.ui.game.state.CarState
import com.example.iapgame.racingcar_game.ui.game.state.DialogState
import com.example.iapgame.racingcar_game.ui.game.state.GameState
import com.example.iapgame.racingcar_game.ui.home.GameOverScreen
import com.example.iapgame.racingcar_game.ui.home.GamePausedScreen
import com.example.iapgame.racingcar_game.ui.home.HomeScreen
import com.example.iapgame.racingcar_game.ui.models.AccelerationData
import com.example.iapgame.racingcar_game.ui.models.CarPosition
import com.example.iapgame.racingcar_game.ui.models.MovementInput
import com.example.iapgame.racingcar_game.ui.models.MovementInput.Accelerometer
import com.example.iapgame.racingcar_game.ui.models.MovementInput.SwipeGestures
import com.example.iapgame.racingcar_game.ui.models.MovementInput.TapGestures
import com.example.iapgame.racingcar_game.ui.models.RacingResourcePack
import com.example.iapgame.racingcar_game.ui.shop.CarInfo
import com.example.iapgame.racingcar_game.ui.shop.ShopScreen
import com.example.iapgame.racingcar_game.utils.Constants
import com.example.iapgame.racingcar_game.utils.Constants.CAR_MOVEMENT_SPRING_ANIMATION_STIFFNESS
import com.example.iapgame.racingcar_game.utils.Constants.TICKER_ANIMATION_DURATION

@Composable
fun RacingGameScreen(
    shopCars: () -> List<CarInfo>,
    availableCoins: () -> Int,
    gameScore: () -> Int,
    finalScore: () -> Int,
    highscore: () -> Int,
    acceleration: () -> AccelerationData,
    movementInput: () -> MovementInput,
    resourcePack: () -> RacingResourcePack,
    onGameScoreIncrease: () -> Unit,
    onBlockerRectsDraw: (List<Rect>) -> Unit,
    onCarRectDraw: (Rect) -> Unit,
    modifier: Modifier = Modifier,
    exitGame: () -> Unit,
    newCarSelected: (CarInfo) -> Unit,
    buyCar: (CarInfo) -> Unit,
    creditCoins: (Int) -> Unit,
    isCarCollided: () -> Boolean,
    restartGame: () -> Unit
) {
    // resources
    val carImageDrawableBitmap = ImageBitmap.imageResource(resourcePack().carImageDrawable)
    val backgroundImageBitmap = ImageBitmap.imageResource(resourcePack().backgroundImageDrawable)
    val blockerImageBitmap = ImageBitmap.imageResource(resourcePack().blockerImageDrawable)
    val context = LocalContext.current
    // states
    val gameState by remember {
        mutableStateOf(GameState())
    }
    // states
    val dialogState by remember {
        mutableStateOf(DialogState())
    }
    val carState by remember {
        mutableStateOf(
            CarState(image = carImageDrawableBitmap)
        )
    }
    carState.changeImage(carImageDrawableBitmap)
    val blockersState by remember {
        mutableStateOf(
            BlockersState(image = blockerImageBitmap)
        )
    }
    val backgroundState by remember {
        mutableStateOf(BackgroundState(image = backgroundImageBitmap, onGameScoreIncrease = {
            if (gameState.isRunning()) onGameScoreIncrease()
        }))
    }

    val backgroundSpeed by remember {
        derivedStateOf {
            (gameScore() / Constants.GAME_SCORE_TO_VELOCITY_RATIO) + Constants.INITIAL_VELOCITY
        }
    }

    if (isCarCollided()) {
        gameState.gameOver()
    }

    // ticker
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")

    val ticker by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = TICKER_ANIMATION_DURATION, easing = LinearEasing)
        ), label = "ticker"
    )

    BoxWithConstraints(modifier = modifier) {
        ticker //todo find a better way to put it in here!

        LaunchedEffect(movementInput()) {
            if (movementInput() == Accelerometer) carState.moveWithAcceleration(acceleration())
        }

        val carOffsetIndex by animateFloatAsState(
            targetValue = carState.getPosition().fromLeftOffsetIndex(),
            label = "car offset index",
            animationSpec = spring(stiffness = CAR_MOVEMENT_SPRING_ANIMATION_STIFFNESS)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(if (gameState.isRunning()) {
                    when (movementInput()) {
                        TapGestures -> Modifier.detectCarPositionByPointerInput(maxWidth = maxWidth.value.toInt()) { position ->
                            carState.moveWithTapGesture(position)
                        }

                        SwipeGestures -> Modifier.detectSwipeDirection(maxWidth.value.toInt()) { swipeDirection ->
                            carState.moveWithSwipeGesture(swipeDirection)
                        }

                        Accelerometer -> Modifier
                    }
                } else {
                    Modifier
                }
                )
        )
        {
            GameCanvas(
                gameState = gameState,
                backgroundState = backgroundState,
                backgroundSpeed = backgroundSpeed,
                blockersState = blockersState,
                carState = carState,
                carOffsetIndex = carOffsetIndex,
                onBlockerRectsDraw = onBlockerRectsDraw,
                onCarRectDraw = onCarRectDraw,
                modifier = Modifier.fillMaxSize(),
            )
            if (!gameState.isRunning()) {
                if (dialogState.isHome()) {
                    if (gameState.isStopped()) {
                        HomeScreen(highscore(), exitGame = exitGame, startGame = {
                            Toast
                                .makeText(
                                    context,
                                    "Tap lane to move car!",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                            gameState.run()
                        }, shop = {
                            dialogState.showShop()
                        })
                    } else if (gameState.isGameOver()) {
                        GameOverScreen(finalScore(), exitGame = exitGame,
                            restartGame = {
                                gameState.run()
                                backgroundState.reset()
                                blockersState.reset()
                                carState.changePosition(CarPosition.Middle)
                            }, shop = {
                                dialogState.showShop()
                            })
                    } else {
                        GamePausedScreen(gameScore(), exitGame = exitGame,
                            resumeGame = {
                                gameState.run()
                            }, shop = {
                                dialogState.showShop()
                            })
                    }
                } else if (dialogState.isShop()) {
                    ShopScreen(
                        shopCars = {
                            shopCars()
                        },
                        availableCoins = {
                            availableCoins()
                        },
                        showHome = {
                            dialogState.showHome()
                        },
                        newCarSelected = newCarSelected,
                        buyCar = buyCar,
                        creditCoins = creditCoins
                    )
                }
            }
        }
        if (gameState.isRunning()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                GameTopBar(
                    gameScore = gameScore, showHome = {
                        gameState.pause()
                        dialogState.showHome()
                    }, modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
