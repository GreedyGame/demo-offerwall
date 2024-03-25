package com.example.iapgame.racingcar_game.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.iapgame.racingcar_game.ui.game.RacingGameScreen
import com.example.iapgame.racingcar_game.ui.viewmodel.MainViewModel
import com.example.iapgame.racingcar_game.utils.vibrateError
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import org.koin.androidx.compose.koinViewModel

sealed class Destinations(val route: String) {
    data object Game : Destinations("game")
    data object GetReady : Destinations("get_ready")
    data object Store : Destinations("store")
}

@Composable
@OptIn(ExperimentalMaterialNavigationApi::class)
fun RacingCarGameNavHost(viewModel: MainViewModel = koinViewModel(), exitGame: () -> Unit) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)
    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        NavHost(navController, Destinations.Game.route) {
            gameScreen(navController = navController, viewModel = viewModel, exitGame = exitGame)
            storeScreen(navController = navController, viewModel = viewModel)
        }
    }
}

private fun NavGraphBuilder.storeScreen(
    navController: NavHostController, viewModel: MainViewModel
) {
}

private fun NavGraphBuilder.gameScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    exitGame: () -> Unit
) {
    composable(Destinations.Game.route) {

        val context = LocalContext.current
        LaunchedEffect(context) {
            viewModel.vibrateSharedFlow.collect {
                context.vibrateError()
            }
        }

        val availableCoins by viewModel.availableCoins.collectAsState()
        val shopCars by viewModel.cars.collectAsState()
        val gameScore by viewModel.gameScore.collectAsState()
        val highscore by viewModel.highscore.collectAsState()
        val acceleration by viewModel.acceleration.collectAsState()
        val movementInput by viewModel.movementInput.collectAsState()
        val resourcePack by viewModel.resourcePack.collectAsState()

        RacingGameScreen(
            shopCars = { shopCars },
            availableCoins = { availableCoins },
            gameScore = { gameScore },
            highscore = { highscore },
            resourcePack = { resourcePack },
            acceleration = { acceleration },
            movementInput = { movementInput },
            onGameScoreIncrease = viewModel::increaseGameScore,
            onBlockerRectsDraw = viewModel::updateBlockerRects,
            onCarRectDraw = viewModel::updateCarRect,
            modifier = Modifier.fillMaxSize(),
            exitGame = {
                exitGame()
            },
            newCarSelected = {
                viewModel.newCarSelected(it)
            },
            buyCar = {
                viewModel.buyCar(it)
            },
            creditCoins = {
                viewModel.creditCoins(it)
            }
        )
    }
}
