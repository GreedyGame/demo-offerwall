package com.example.iapgame.racingcar_game.ui.shop


import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.iapgame.R
import com.example.iapgame.racingcar_game.ui.theme.CarNotOwned
import com.example.iapgame.racingcar_game.ui.theme.CoinCTA
import com.example.iapgame.racingcar_game.ui.theme.WalletSolid
import com.example.iapgame.racingcar_game.ui.theme.WalletStroke
import com.example.iapgame.racingcar_game.ui.theme.White50
import com.example.iapgame.racingcar_game.ui.theme.pricedown
import com.pubscale.sdkone.offerwall.OfferWall
import com.pubscale.sdkone.offerwall.models.OfferWallListener
import com.pubscale.sdkone.offerwall.models.Reward

@Composable
fun ShopScreen(
    shopCars: () -> List<CarInfo>,
    availableCoins: () -> Int,
    showHome: () -> Unit,
    newCarSelected: (CarInfo) -> Unit,
    buyCar: (CarInfo) -> Unit,
    creditCoins: (Int) -> Unit
) {
    Dialog(
        onDismissRequest = { showHome() }, properties = DialogProperties(
            dismissOnClickOutside = false, usePlatformDefaultWidth = false
        )
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {
            ShopTopRow(availableCoins = availableCoins, showHome = showHome)
            Box(modifier = Modifier.weight(1f)) {
                ShowItems(
                    shopCars = shopCars,
                    newCarSelected = newCarSelected,
                    availableCoins = availableCoins,
                    buyCar = buyCar
                )
            }
            GetFreeCoinsButton(creditCoins = creditCoins)
        }
    }
}

@Composable
fun ShopTopRow(availableCoins: () -> Int, showHome: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(top = 6.dp, start = 15.dp, end = 15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(50.dp))
                .background(color = White50)
        ) {
            Button(
                modifier = Modifier.padding(8.dp, 3.dp),
                shape = RoundedCornerShape(50.dp),
                border = BorderStroke(width = 2.dp, color = WalletStroke),
                colors = ButtonDefaults.buttonColors(WalletSolid),
                onClick = { },
                contentPadding = PaddingValues(0.dp),
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .width(90.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_game_coin),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Box(modifier = Modifier.padding(start = 6.dp, bottom = 2.dp)) {
                        Text(
                            modifier = Modifier.padding(start = 3.dp, top = 3.dp),
                            text = "${availableCoins()}",
                            fontFamily = pricedown,
                            color = Color.Black,
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )
                        Text(
                            text = "${availableCoins()}",
                            fontFamily = pricedown,
                            color = Color.Black,
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )
                        Text(
                            modifier = Modifier.padding(start = 1.dp, top = 1.dp),
                            text = "${availableCoins()}",
                            fontFamily = pricedown,
                            color = Color.White,
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )
                    }
                }
            }
        }
        Image(painter = painterResource(id = R.drawable.ic_game_shop_close),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clickable { showHome() })
    }
}

@Composable
fun ShowItems(
    shopCars: () -> List<CarInfo>, newCarSelected: (CarInfo) -> Unit,
    availableCoins: () -> Int, buyCar: (CarInfo) -> Unit,
) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        Text(
            text = "shop",
            modifier = Modifier
                .wrapContentWidth()
                .padding(10.dp),
            fontFamily = pricedown,
            color = Color.White,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
        val iconSize = 36.dp
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 30.dp - (iconSize / 2), end = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(shopCars()) {
                val offsetInPx = LocalDensity.current.run { (iconSize / 2).roundToPx() }
                Box(modifier = Modifier
                    .padding(
                        start = iconSize / 2, top = iconSize / 2, bottom = iconSize / 2
                    )
                    .clickable {
                        if (it.isSelected) {
                            return@clickable
                        }
                        if (it.carIsOwned) {
                            newCarSelected(it)
                            return@clickable
                        }
                        if (availableCoins() < it.carCost) {
                            Toast
                                .makeText(context, "Insufficient balance!", Toast.LENGTH_SHORT)
                                .show()
                            return@clickable
                        }
                        buyCar(it)
                    }) {
                    val bgColor = if (it.carIsOwned) {
                        Color.White
                    } else {
                        CarNotOwned
                    }
                    Card(
                        modifier = Modifier.aspectRatio(1f),
                        colors = CardDefaults.cardColors(bgColor),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Image(
                            painter = painterResource(id = it.carImage),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 20.dp)
                        )
                    }
                    if (it.isSelected) {
                        Image(painter = painterResource(id = R.drawable.ic_game_car_selected),
                            contentDescription = null,
                            modifier = Modifier
                                .offset {
                                    IntOffset(x = -offsetInPx, y = -offsetInPx)
                                }
                                .size(iconSize))
                    }
                    if (!it.carIsOwned) {
                        Button(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .align(Alignment.BottomCenter)
                                .offset {
                                    IntOffset(x = 0, y = +offsetInPx)
                                },
                            border = BorderStroke(width = 2.dp, color = WalletStroke),
                            colors = ButtonDefaults.buttonColors(WalletSolid),
                            onClick = { },
                            contentPadding = PaddingValues(0.dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_game_coin),
                                    contentDescription = null
                                )
                                Box(modifier = Modifier.padding(start = 6.dp, bottom = 2.dp)) {
                                    Text(
                                        modifier = Modifier.padding(start = 3.dp, top = 3.dp),
                                        text = "${it.carCost}",
                                        fontFamily = pricedown,
                                        color = Color.Black,
                                        fontSize = 25.sp,
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(
                                            platformStyle = PlatformTextStyle(
                                                includeFontPadding = false
                                            )
                                        )
                                    )
                                    Text(
                                        text = "${it.carCost}",
                                        fontFamily = pricedown,
                                        color = Color.Black,
                                        fontSize = 25.sp,
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(
                                            platformStyle = PlatformTextStyle(
                                                includeFontPadding = false
                                            )
                                        )
                                    )
                                    Text(
                                        modifier = Modifier.padding(start = 1.dp, top = 1.dp),
                                        text = "${it.carCost}",
                                        fontFamily = pricedown,
                                        color = Color.White,
                                        fontSize = 25.sp,
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(
                                            platformStyle = PlatformTextStyle(
                                                includeFontPadding = false
                                            )
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GetFreeCoinsButton(creditCoins: (Int) -> Unit) {
    val context = LocalContext.current
    Button(
        modifier = Modifier.padding(
            start = 30.dp, end = 30.dp, top = 28.dp, bottom = 10.dp
        ),
        onClick = {
            context.getActivity()?.let {
                launchOfferwall(it) { coins ->
                    creditCoins(coins)
                }
            }
        },
        shape = RoundedCornerShape(19.dp),
        border = BorderStroke(3.dp, Color.Black),
        colors = ButtonDefaults.buttonColors(CoinCTA)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_game_coin), contentDescription = null
            )
            Box(modifier = Modifier.padding(start = 6.dp, bottom = 2.dp)) {
                Text(
                    modifier = Modifier.padding(start = 2.dp, top = 2.dp),
                    text = "Get free coins",
                    fontFamily = pricedown,
                    color = Color.Black,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
                Text(
                    text = "Get free coins",
                    fontFamily = pricedown,
                    color = Color.White,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
            }
        }
    }
}

fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

fun launchOfferwall(activity: ComponentActivity, creditCoins: (Int) -> Unit) {
    val offerWallListener = object : OfferWallListener {

        override fun onOfferWallShowed() {
        }

        override fun onOfferWallClosed() {
        }

        override fun onRewardClaimed(reward: Reward) {
            creditCoins(reward.amount.toInt())
        }

        override fun onFailed(message: String) {
        }
    }
    OfferWall.launch(activity, offerWallListener)
}

data class CarInfo(
    val carId: String = "",
    @DrawableRes val carImage: Int = 0,
    val carCost: Int = 0,
    val carIsOwned: Boolean = false,
    val isSelected: Boolean = false
)