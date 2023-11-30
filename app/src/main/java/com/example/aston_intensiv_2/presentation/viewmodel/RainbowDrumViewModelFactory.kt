package com.example.aston_intensiv_2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aston_intensiv_2.data.RainbowDrumRepo

class RainbowDrumViewModelFactory(private val repo: RainbowDrumRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RainbowDrumViewModel::class.java)) {
            return RainbowDrumViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}