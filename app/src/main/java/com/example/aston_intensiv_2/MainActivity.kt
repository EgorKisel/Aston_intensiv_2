package com.example.aston_intensiv_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val wheelView = findViewById<RainbowDrumView>(R.id.wheelView)
        val spinButton = findViewById<Button>(R.id.spinButton)
        val resetButton = findViewById<Button>(R.id.resetButton)
        val sizeSeekBar = findViewById<SeekBar>(R.id.sizeSeekBar)
        val textField = findViewById<TextView>(R.id.textField)
        val imageField = findViewById<ImageView>(R.id.imageField)

        spinButton.setOnClickListener {
            wheelView.spin()
        }

        resetButton.setOnClickListener {
            wheelView.reset()
        }

        wheelView.setTextField(textField)
        wheelView.setImageField(imageField)

        sizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val newSize = progress + 1390
                wheelView.drumSize = newSize.toFloat() * 0.6f
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}