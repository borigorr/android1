package ru.netology.nmedia.helpers

import java.math.RoundingMode
import java.text.DecimalFormat

object NumberHelper {
     fun intToShortString(num: Int):  String {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.DOWN

        if (num >= 1_000_000) {
            val roundNum = df.format(num.toDouble() / 1_000_000.0)
            return "${roundNum}M"
        }
        if (num >= 1_000) {
            val roundNum = df.format(num.toDouble() / 1_000.0)
            return "${roundNum}K"
        }
        return num.toString()
    }
}