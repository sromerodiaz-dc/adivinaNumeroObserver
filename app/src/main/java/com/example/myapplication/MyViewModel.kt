package com.example.myapplication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    private val TAG_LOG:String = "viewModel Debug"

    // LiveData para observar el estado actual
    val estadoLiveData: MutableLiveData<ESTADOS> = MutableLiveData(ESTADOS.EMPEZANDO) // El estado del juego es observado
    private var numero = Datos.numbers.intValue
    private var numeros = Datos._numbers

    init {
        Log.d(TAG_LOG,"Inicializado viewModel")
    }

    fun onCreate() {
        onStart()
        createRandom()
    }

    private fun createRandom() {
        // Asigna numero aleatorios al numero que debe ser adivinado y a los numero con los que sera comparado
        numero = (0..3).random()
        numeros = (0..3).toList().shuffled().toMutableList()

        // Avisa por Log
        Log.d(TAG_LOG,"Numeros asignados")
    }

    fun compareNumbers(n: Int) {
        onNumber()
        Log.d(TAG_LOG, "Comprobando números")

        if (numero == numeros[n]) {
            Log.d(TAG_LOG, "¡Los números coinciden!")
            onMatch()
        } else {
            Log.d(TAG_LOG, "Los números no coinciden.")
            onStart()
        }
    }

    private fun onStart() {
        estadoLiveData.value = ESTADOS.EMPEZANDO
    }

    private fun onNumber() {
        estadoLiveData.value = ESTADOS.COMPROBANDO
    }

    private fun onMatch() {
        estadoLiveData.value = ESTADOS.FINALIZADO
    }
}