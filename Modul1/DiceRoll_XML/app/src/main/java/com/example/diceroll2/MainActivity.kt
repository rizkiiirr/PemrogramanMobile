package com.example.diceroll

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.diceroll2.R

class MainActivity : AppCompatActivity() {

    private lateinit var diceImage1: ImageView
    private lateinit var diceImage2: ImageView
    private lateinit var rollButton: Button
    private lateinit var coordinatorLayout: androidx.coordinatorlayout.widget.CoordinatorLayout

    private val diceImages = listOf(
        R.drawable.dice_0,
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        diceImage1 = findViewById(R.id.dice_image1)
        diceImage2 = findViewById(R.id.dice_image2)
        rollButton = findViewById(R.id.roll_button)

        rollButton.setOnClickListener {
            val result1 = (1..6).random()
            val result2 = (1..6).random()

            diceImage1.setImageResource(diceImages[result1])
            diceImage2.setImageResource(diceImages[result2])

            val message = if (result1 == result2) {
                "Selamat, anda dapat dadu double!"
            } else {
                "Anda belum beruntung!"
            }

            Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show()
        }
    }
}