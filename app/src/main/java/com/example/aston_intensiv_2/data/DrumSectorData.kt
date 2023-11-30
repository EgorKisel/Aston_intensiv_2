package com.example.aston_intensiv_2.data

import android.graphics.Paint

class DrumSectorData {
    val drumSector = HashMap<String, DrumSector>()
    private val maxAngle = 360f
    private val sectorNumber = 7
    // убрать в RainbowDrumView?
    val sweepAngle = maxAngle / sectorNumber

    fun addSector(name: String, color: Paint) {
        drumSector[name] = DrumSector(name, 0f, 0f, color)
    }
}