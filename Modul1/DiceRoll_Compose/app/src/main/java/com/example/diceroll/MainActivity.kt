package com.example.diceroll

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceroll.ui.theme.DiceRollTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiceRollTheme {
                DiceRollerApp()
            }
        }
    }

    @Preview
    @Composable
    fun DiceRollerApp() {
        DiceWithButtonAndImage()
    }

    @Composable
    fun DiceWithButtonAndImage(
        modifier: Modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        var dismissJob by remember { mutableStateOf<Job?>(null) }

        var result1 by remember { mutableStateOf(0) }
        var result2 by remember { mutableStateOf(0) }
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()

        val dice1 = when (result1) {
            0 -> R.drawable.dice_0
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        val dice2 = when (result2) {
            0 -> R.drawable.dice_0
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }

        ) { padding ->
            Column(
                modifier = modifier.padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(dice1),
                        contentDescription = result1.toString()
                    )
                    Image(
                        painter = painterResource(dice2),
                        contentDescription = result2.toString()
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = {
                    result1 = (1..6).random()
                    result2 = (1..6).random()

                    val message = if (result1 == result2) {
                        "Selamat, anda dapat dadu double!"
                    } else {
                        "Anda belum beruntung!"
                    }
                        //DELAY YANG LAMA
//                    coroutineScope.launch {
//                        snackbarHostState.showSnackbar(pesan)
//                    }

                        //LEBIH CEPAT NAMUN SNACKBAR TIDAK BERHENTI
//                    coroutineScope.launch {
//                        snackbarHostState.currentSnackbarData?.dismiss()
//
//                        snackbarHostState.showSnackbar(
//                            message = pesan,
//                            duration = SnackbarDuration.Indefinite
//                        )
//
//                        kotlinx.coroutines.delay(1000) // delay 0.5 detik
//
//                    }

                    coroutineScope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()

                        snackbarHostState.showSnackbar(
                            message = message,
                            duration = SnackbarDuration.Indefinite
                        )
                    }

                    dismissJob?.cancel()

                    dismissJob = coroutineScope.launch {
                        kotlinx.coroutines.delay(2000)
                        snackbarHostState.currentSnackbarData?.dismiss()
                    }
                }) {
                    Text(stringResource(R.string.roll))
                }
            }
        }
    }
}