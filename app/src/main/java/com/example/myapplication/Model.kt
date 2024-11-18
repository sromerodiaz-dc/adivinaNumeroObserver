package com.example.myapplication

import androidx.compose.runtime.mutableIntStateOf

enum class ESTADOS(var hasWon: Boolean, var hasStart: Boolean) {
    EMPEZANDO(hasWon = false, hasStart = true),
    COMPROBANDO(hasWon = false, hasStart = false),
    FINALIZADO(hasWon = true, hasStart = false)
}

object Datos {
    val numbers = mutableIntStateOf(0)
    val _numbers = mutableListOf(0)
}