package com.example.iapgame.racingcar_game.ui.get_ready

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.iapgame.R
import com.example.iapgame.racingcar_game.ui.theme.Brown
import com.example.iapgame.racingcar_game.ui.theme.Green
import com.example.iapgame.racingcar_game.ui.theme.Orange
import com.example.iapgame.racingcar_game.ui.theme.minecraft
import com.example.iapgame.racingcar_game.ui.theme.pricedown

@Composable
fun GetReadyScreen(
    gameScore: Int, exitGame: () -> Unit, startGame: () -> Unit
) {
    Dialog(onDismissRequest = { exitGame() }) {
        Column(
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Text(
                    modifier = Modifier.padding(start = 5.dp, top = 5.dp),
                    text = "$gameScore",
                    fontFamily = pricedown,
                    color = Color.Black,
                    fontSize = 80.sp
                )
                Text(
                    text = "$gameScore",
                    fontFamily = pricedown,
                    color = Color.White,
                    fontSize = 80.sp
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )
            Box {
                Text(
                    modifier = Modifier.padding(start = 5.dp, top = 5.dp),
                    text = "Highway\nRider",
                    fontFamily = pricedown,
                    color = Color.Black,
                    fontSize = 50.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 46.sp
                )
                Text(
                    text = "Highway\nRider",
                    fontFamily = pricedown,
                    color = Color.White,
                    fontSize = 50.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 46.sp
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(17.dp)
            )
            Box {
                Text(
                    text = "Get Ready",
                    fontFamily = pricedown,
                    color = Green,
                    fontSize = 40.sp,
                )
                Text(
                    text = "Get Ready",
                    fontFamily = pricedown,
                    fontSize = 40.sp,
                    style = LocalTextStyle.current.merge(
                        TextStyle(
                            color = Color.Black, fontSize = 40.sp, drawStyle = Stroke(
                                miter = 10f,
                                width = 3f,
                                join = StrokeJoin.Bevel,
                            )
                        )
                    )
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(17.dp)
            )
            Image(painter = painterResource(id = R.drawable.ic_tap_tap),
                contentDescription = "Tap here",
                modifier = Modifier.clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() } // This is mandatory
                ) {
                    startGame()
                }
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )

            Button(
                shape = RoundedCornerShape(0),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .border(7.dp, Brown)
                    .padding(7.dp),
                onClick = { },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "SHOP",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(all = 7.dp)
                        .background(color = Orange)
                        .padding(vertical = 11.dp, horizontal = 53.dp),
                    fontFamily = minecraft
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)
            )
            Button(
                shape = RoundedCornerShape(0),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .border(7.dp, Brown)
                    .padding(7.dp),
                onClick = exitGame,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "EXIT",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(all = 7.dp)
                        .background(color = Orange)
                        .padding(vertical = 11.dp, horizontal = 53.dp),
                    fontFamily = minecraft
                )
            }
        }
    }
}