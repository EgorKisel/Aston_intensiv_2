package com.example.aston_intensiv_2.data

import android.graphics.Color
import android.graphics.Paint

class RainbowDrumRepo {

    fun getDrumSector(): DrumSectorData {
        val drumSectorData = DrumSectorData()
        colors.forEach { sector ->
            drumSectorData.addSector(sector.first, sector.second)
        }
        return drumSectorData
    }

    private val colors = listOf(
        Pair("RED", setColor("#FF0000")),
        Pair("ORANGE", setColor("#FF8C00")),
        Pair("YELLOW", setColor("#FFFF00")),
        Pair("GREEN", setColor("#008000")),
        Pair("BLUE", setColor("#9FC5E8")),
        Pair("NAVY_BLUE", setColor("#0000FF")),
        Pair("PURPLE", setColor("#8A2BE2")),
    )

    private fun setColor(color: String): Paint {
        val paint = Paint()
        paint.color = Color.parseColor(color)
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        return paint
    }
}