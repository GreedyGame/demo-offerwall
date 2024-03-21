package com.example.iapgame.racingcar_game.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.iapgame.R

@Composable
fun StoreScreen(modifier: Modifier) {
    BoxWithConstraints(modifier = modifier) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = modifier
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_block1),
                        contentDescription = "pause"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Pause")
                }
                Button(
                    onClick = { },
                ) {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Filled.Close),
                        contentDescription = "close"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Close")
                }
            }
        }
    }
}
