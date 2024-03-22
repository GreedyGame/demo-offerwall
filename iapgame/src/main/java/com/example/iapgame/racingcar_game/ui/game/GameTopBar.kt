package com.example.iapgame.racingcar_game.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iapgame.R
import com.example.iapgame.racingcar_game.ui.theme.pricedown

@Composable
fun GameTopBar(gameScore: () -> Int, showHome: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.padding(start = 20.dp)) {
            Text(
                modifier = Modifier.padding(start = 5.dp, top = 5.dp),
                text = "${gameScore()}",
                fontFamily = pricedown,
                color = Color.Black,
                fontSize = 80.sp
            )
            Text(
                text = "${gameScore()}",
                fontFamily = pricedown,
                color = Color.White,
                fontSize = 80.sp
            )
        }
        Box(modifier = Modifier.padding(top = 15.dp, end = 20.dp)) {
            Image(
                modifier = Modifier
                    .size(60.dp)
                    .clickable {
                        showHome()
                    },
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = "settings",
            )
        }
    }
}

