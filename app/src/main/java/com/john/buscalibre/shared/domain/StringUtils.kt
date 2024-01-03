package com.john.buscalibre.shared.domain

import android.icu.text.DecimalFormat
import java.util.Locale

fun Double.floatToCurrencyFormat(): String {
    val decimalFormat = DecimalFormat.getCurrencyInstance(Locale("es", "CO")) as DecimalFormat
    decimalFormat.maximumFractionDigits = 0
    return decimalFormat.format(this)
}
