package com.example.iapgame.racingcar_game.ui.game

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import com.example.iapgame.racingcar_game.ui.models.CarPosition
import com.example.iapgame.racingcar_game.utils.Constants

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.detectCarPositionByPointerInput(
    maxWidth: Int,
    onDetectPosition: (CarPosition) -> Unit
) = Modifier.then(pointerInteropFilter { motionEvent ->
    val currentX = motionEvent.x

    val laneIndex = (currentX / maxWidth)
        .toInt()
        .coerceIn(0, Constants.LANE_COUNT)

    CarPosition.values()
        .find { position ->
            position
                .fromLeftOffsetIndex()
                .toInt() == laneIndex
        }?.let { position ->
            onDetectPosition(position)
        }

    true
})