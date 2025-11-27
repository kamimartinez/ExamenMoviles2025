// Indica el paquete al que pertenece el archivo
package com.app.examen2025.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.app.examen2025.presentation.navigation.NavGraph
import com.app.examen2025.presentation.theme.Examen2025Theme
import dagger.hilt.android.AndroidEntryPoint

// hilt necesita saber cuales son los puntos de inicio de nuestra aplicación
// Clase principal que representa la pantalla base de la app.

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Se ejecuta cuando la Activity se crea
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilita el modo edge-to-edge:
        enableEdgeToEdge()
        // Todo lo que esté dentro de setContent será lo que se renderice en pantalla.
        setContent {
            // Aplica el tema global de la app
            Examen2025Theme {
                // Scaffold crea una estructura básica para la pantalla.
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // El gráfico de navegación que se encarga de mostrar las pantallas (Home, Detail, etc.)
                    NavGraph()
                }
            }
        }
    }
}
