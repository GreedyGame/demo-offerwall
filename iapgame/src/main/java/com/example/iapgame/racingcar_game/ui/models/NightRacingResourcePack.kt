package com.example.iapgame.racingcar_game.ui.models

import com.example.iapgame.R

data class NightRacingResourcePack(
    override val backgroundImageDrawable: Int = R.drawable.bg_road_night,
    override val carImageDrawable: Int = R.drawable.ic_car_night,
    override val blockerImageDrawable: Int = R.drawable.ic_block_night
) : RacingResourcePack