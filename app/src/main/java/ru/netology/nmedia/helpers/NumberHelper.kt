package ru.netology.nmedia.helpers

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object NumberHelper {
    fun intToShortString(num: Int): String {

        val df = DecimalFormat("#0.#")
        df.roundingMode = RoundingMode.DOWN
        df.decimalFormatSymbols = DecimalFormatSymbols(Locale.US)

        if (num >= 1_000_000) {
            val roundNum = df.format(num.toDouble() / 1_000_000.0)
            return "${roundNum}M"
        }

        if (num >= 10_000) {
            val roundNum = (num.toDouble() / 1_000.0).toInt()
            return "${roundNum}K"
        }

        if (num >= 1_000) {
            val roundNum = df.format(num.toDouble() / 1_000.0)
            return "${roundNum}K"
        }
        return num.toString()
    }
}