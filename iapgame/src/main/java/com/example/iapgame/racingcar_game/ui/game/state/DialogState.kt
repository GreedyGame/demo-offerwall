package com.example.iapgame.racingcar_game.ui.game.state

data class DialogState(private var type: Type = Type.HOME) {
    enum class Type {
        HOME, SHOP
    }

    fun isHome() = type == Type.HOME
    fun isShop() = type == Type.SHOP

    fun showHome() {
        type = Type.HOME
    }

    fun showShop() {
        type = Type.SHOP
    }
}
