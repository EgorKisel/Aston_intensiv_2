package com.example.aston_intensiv_2.presentation

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.request.CachePolicy
import com.example.aston_intensiv_2.R
import com.example.aston_intensiv_2.data.DrumSectorData
import com.example.aston_intensiv_2.data.RainbowDrumRepo
import com.example.aston_intensiv_2.databinding.ActivityMainBinding
import com.example.aston_intensiv_2.presentation.custom_view.CustomTextView
import com.example.aston_intensiv_2.presentation.viewmodel.RainbowDrumViewModel
import com.example.aston_intensiv_2.presentation.viewmodel.RainbowDrumViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var drumSectorData: DrumSectorData? = null
    private val repo = RainbowDrumRepo()

    private val viewModel: RainbowDrumViewModel by viewModels {
        RainbowDrumViewModelFactory(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { state ->
            drumSectorData = state.drumSectorData
            binding.wheelView.apply {
                setData(drumSectorData!!)
                setDoOnAnimationEnd { winner ->
                    when (winner) {
                        "RED", "YELLOW", "BLUE", "PURPLE" -> loadText()
                        "ORANGE", "GREEN", "NAVY_BLUE" -> loadImage()
                    }
                }
            }
            binding.wheelView.animateScaling(state.drumSize / 100f)
        }
        viewModel.getData()

        binding.spinButton.setOnClickListener {
            binding.wheelView.animateSpinning()
        }

        binding.resetButton.setOnClickListener {
            binding.container.removeAllViews()
        }

        binding.sizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.changeDrumSize(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun loadText() {
        binding.container.removeAllViews()
        binding.container.addView(CustomTextView(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setText("ПРИВЕТ!", R.color.black, 100f)
        })
    }

    private fun loadImage() {
        binding.container.addView(ImageView(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            load("https://loremflickr.com/320/240/dog") {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder)
                diskCachePolicy(CachePolicy.DISABLED)
                memoryCachePolicy(CachePolicy.DISABLED)
            }
        })
    }
}