package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * GUI
 * */

@Composable
fun IU(viewModel: MyViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Boton(viewModel)
        Cuadrantes(viewModel)
    }
}

@Composable
fun GameScreen(viewModel: MyViewModel) {
    // Observa el estado hasWon
    var _active by remember {
        mutableStateOf(viewModel.estadoLiveData.value)
    }

    viewModel.estadoLiveData.observe(LocalLifecycleOwner.current) {
        // Log.d(TAG_LOG, "Oserver Estado: ${miViewModel.estadoLiveData.value!!.name}")
        _active = viewModel.estadoLiveData.value!!
    }
    val TAG_LOG = "miDebug"

    // Usar un Box para centrar el contenido
    Box(
        modifier = Modifier.fillMaxSize(), // Ocupa todo el tamaño de la pantalla
        contentAlignment = Alignment.Center // Centra el contenido en el Box
    ) {
        if (_active?.hasWon == false && _active?.hasStart == true) {
            IU(viewModel = viewModel)
            Log.d(TAG_LOG, "empezando...")
        } else {
            Text(text = "¡Has ganado!", fontSize = 24.sp)
            // Offset the button 150 pixels down
            Button(
                // utilizamos el color del enum
                colors = ButtonDefaults.buttonColors(Color.Green),
                onClick = {
                    _active?.hasStart = true
                    _active?.hasWon = false
                },
                modifier = Modifier
                    .size(80.dp, 40.dp)
                    .offset(y = 150.dp) // Move the button 150 pixels down
            ) {
                // utilizamos el texto del enum
                Text("Volver a empezar", fontSize = 10.sp)
            }
        }
    }
}

@Composable
fun Boton(viewModel: MyViewModel) {
    val TAG_LOG = "GUI debug"

    var _active by remember {
        mutableStateOf(viewModel.estadoLiveData.value)
    }

    viewModel.estadoLiveData.observe(LocalLifecycleOwner.current) {
        // Log.d(TAG_LOG, "Oserver Estado: ${miViewModel.estadoLiveData.value!!.name}")
        _active = viewModel.estadoLiveData.value!!
    }

    if (_active?.hasStart == true) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(Color.Red),
                onClick = {
                    Log.d(TAG_LOG,"Click!")
                    viewModel.onCreate()
                },
                modifier = Modifier
                    .size(80.dp,40.dp)
                    .offset(y = 150.dp)
            ) {
                Text("START", fontSize = 10.sp)
            }
        }
    }
}

@Composable
fun Cuadrantes(viewModel: MyViewModel) {
    val TAG_LOG = "GUI debug"

    var _active by remember {
        mutableStateOf(viewModel.estadoLiveData.value)
    }

    viewModel.estadoLiveData.observe(LocalLifecycleOwner.current) {
        // Log.d(TAG_LOG, "Oserver Estado: ${miViewModel.estadoLiveData.value!!.name}")
        _active = viewModel.estadoLiveData.value!!
    }

    Box(
        modifier = Modifier.fillMaxSize(), // El Box ocupa todo el tamaño disponible
        contentAlignment = Alignment.Center // Centra el contenido dentro del Box
    ) {
        if (_active?.hasStart == false) { // Verifica si el juego ha comenzado
            // Usamos un Box para superponer áreas clicables sobre los arcos
            Box(modifier = Modifier.size(200.dp)) {
                // Dibujamos cada cuadrante con un color diferente
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val canvasWidth = size.width // Ancho del canvas
                    val canvasHeight = size.height // Alto del canvas

                    // Dibuja el cuadrante rojo
                    drawArc(
                        color = Color.Red,
                        startAngle = 0f,
                        sweepAngle = 90f,
                        useCenter = true,
                        size = Size(canvasWidth, canvasHeight),
                        style = Fill
                    )
                    // Dibuja el cuadrante verde
                    drawArc(
                        color = Color.Green,
                        startAngle = 90f,
                        sweepAngle = 90f,
                        useCenter = true,
                        size = Size(canvasWidth, canvasHeight),
                        style = Fill
                    )
                    // Dibuja el cuadrante azul
                    drawArc(
                        color = Color.Blue,
                        startAngle = 180f,
                        sweepAngle = 90f,
                        useCenter = true,
                        size = Size(canvasWidth, canvasHeight),
                        style = Fill
                    )
                    // Dibuja el cuadrante amarillo
                    drawArc(
                        color = Color.Yellow,
                        startAngle = 270f,
                        sweepAngle = 90f,
                        useCenter = true,
                        size = Size(canvasWidth, canvasHeight),
                        style = Fill
                    )
                }

                // Definimos áreas clicables para cada cuadrante
                val quadrantSize = 200.dp / 2 // Tamaño de cada cuadrante

                // Cuadrante rojo
                Box(
                    modifier = Modifier
                        .size(quadrantSize)
                        .align(Alignment.TopStart) // Alinea en la parte superior izquierda
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                Log.d(TAG_LOG, "Dentro del onClick") // Log de clic
                                viewModel.compareNumbers(0) // Llama a la función para comparar con el cuadrante rojo
                            })
                        }
                )

                // Cuadrante verde
                Box(
                    modifier = Modifier
                        .size(quadrantSize)
                        .align(Alignment.TopEnd) // Alinea en la parte superior derecha
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                viewModel.compareNumbers(1) // Llama a la función para comparar con el cuadrante verde
                            })
                        }
                )

                // Cuadrante azul
                Box(
                    modifier = Modifier
                        .size(quadrantSize)
                        .align(Alignment.BottomStart) // Alinea en la parte inferior izquierda
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                viewModel.compareNumbers(2) // Llama a la función para comparar con el cuadrante azul
                            })
                        }
                )

                // Cuadrante amarillo
                Box(
                    modifier = Modifier
                        .size(quadrantSize)
                        .align(Alignment.BottomEnd) // Alinea en la parte inferior derecha
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                viewModel.compareNumbers(3) // Llama a la función para comparar con el cuadrante amarillo
                            })
                        }
                )
            }
        }
    }
}
