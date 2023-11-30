package com.example.aston_intensiv_2.domain

import com.example.aston_intensiv_2.data.DrumSectorData

data class DrumState(
    val drumSectorData: DrumSectorData,
    val drumSize: Int,
)