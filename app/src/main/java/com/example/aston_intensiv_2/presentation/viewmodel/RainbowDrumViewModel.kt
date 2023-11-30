package com.example.aston_intensiv_2.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aston_intensiv_2.data.RainbowDrumRepo
import com.example.aston_intensiv_2.domain.DrumState
import kotlinx.coroutines.launch

class RainbowDrumViewModel(private val repo: RainbowDrumRepo) : ViewModel() {

    private val _data = MutableLiveData<DrumState>()
    val data: LiveData<DrumState> = _data

    fun getData() {
        viewModelScope.launch {
            if (_data.value == null) {
                _data.value = DrumState(
                    drumSectorData = repo.getDrumSector(),
                    drumSize = 50
                )
            }
        }
    }

    fun changeDrumSize(drumSize: Int) {
        viewModelScope.launch {
            _data.value?.let {drumState ->
                _data.postValue(drumState.copy(drumSize = drumSize))
            }
        }
    }
}