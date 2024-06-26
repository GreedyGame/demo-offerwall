package com.example.iapgame.racingcar_game.ui

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.iapgame.racingcar_game.ui.models.MovementInput.Accelerometer
import com.example.iapgame.racingcar_game.ui.theme.RacingCarTheme
import com.example.iapgame.racingcar_game.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity(), SensorEventListener {
    private val viewModel by viewModel<MainViewModel>()

    private val sensorManager by lazy { getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    private val accelerometer by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupCollectors()
        hideStatusAndNavBar()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            RacingCarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    RacingCarGameNavHost(exitGame = {
                        finish()
                    })
                }
            }
        }
    }

    private fun hideStatusAndNavBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowInsetsController =
                WindowCompat.getInsetsController(window, window.decorView)
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )

//            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    private fun setupCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.movementInput.collect {
                    when (it) {
                        Accelerometer -> registerAccelerometer()
                        else -> unregisterAccelerometer()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerAccelerometer()
        viewModel.playBackgroundMusic()
    }

    private fun registerAccelerometer() {
        sensorManager.registerListener(
            this, accelerometer, SensorManager.SENSOR_DELAY_UI   // SENSOR_DELAY_GAME was too much!
        )
    }

    private fun unregisterAccelerometer() {
        sensorManager.unregisterListener(this)
    }

    override fun onPause() {
        super.onPause()
        unregisterAccelerometer()
        viewModel.stopBackgroundMusic()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.releaseSounds()
    }

    override fun onSensorChanged(event: SensorEvent) {
        val accelerationX = event.values[0]
        val accelerationY = event.values[1]
        val accelerationZ = event.values[2]

        viewModel.setAcceleration(accelerationX, accelerationY, accelerationZ)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}