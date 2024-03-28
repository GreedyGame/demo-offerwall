package com.example.iapgame.racingcar_game.ui.game.state

data class GameState(private var status: Status = Status.STOPPED) {
    enum class Status {
        RUNNING, PAUSED, STOPPED, GAME_OVER
    }

    fun isRunning() = status == Status.RUNNING
    fun isPaused() = status == Status.PAUSED
    fun isStopped() = status == Status.STOPPED
    fun isGameOver() = status == Status.GAME_OVER

    fun run() {
        status = Status.RUNNING
    }

    fun pause() {
        status = Status.PAUSED
    }

    fun stop() {
        status = Status.STOPPED
    }

    fun gameOver() {
        status = Status.GAME_OVER
    }

    fun getStatusName(): String {
        return status.name
    }
}
