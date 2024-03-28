package com.example.iapgame.racingcar_game.ui.game.state

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.iapgame.racingcar_game.utils.Constants
import kotlin.random.Random

class BlockersState(private val image: ImageBitmap) {

    private val blockersCount = 100 / Constants.BLOCKER_INTERSPACE_PERCENTAGE
    private var blockers = (1..blockersCount).map {
        BlockState(
            image = image, lanePosition = Random.nextInt(from = 0, until = Constants.LANE_COUNT)
        )
    }

    fun draw(drawScope: DrawScope): List<Rect> {
        val rects = mutableListOf<Rect>()
        drawScope.apply {
            blockers.forEachIndexed { index, blockState ->
                blockState.draw(drawScope = this, index = index, onDraw = { rects.add(it) })
            }
        }
        return rects
    }

    fun move(velocity: Int) {
        blockers.forEach { blockState ->
            blockState.move(velocity = velocity)
        }
    }

    fun reset() {
        blockers = (1..blockersCount).map {
            BlockState(
                image = image, lanePosition = Random.nextInt(from = 0, until = Constants.LANE_COUNT)
            )
        }
    }
}