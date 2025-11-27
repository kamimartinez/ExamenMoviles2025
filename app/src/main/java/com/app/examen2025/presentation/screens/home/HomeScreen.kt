package com.app.examen2025.presentation.screens.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun HomeScreen(
    onRemplazaClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
//    //SECCION DE VARIABLES
//    // Variable que guarda el índice de la pestaña actualmente seleccionada (0 o 1).
//    // "remember" hace que el valor se conserve mientras la composición esté activa.
//    // "mutableStateOf" crea un estado observable que redibuja la UI cuando cambia.
//    var selectedTabIndex by remember { mutableStateOf(0) }
//
//    // Lista de nombres de las pestañas. Se usan para generar dinámicamente los botones de Tab.
//    val tabs = listOf("Remplaza List", "Search")
//    // val mockRemplazaList = remember { Remplaza.getMockData() }
//    // Automáticamente inicia la recolección del StateFlow cuando el composable está activo
//    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//
//    Scaffold (
//        // Barra superior centrada, usando el componente de Material3.
//        topBar = {
//            CenterAlignedTopAppBar(
//                // Título centrado en la barra superior.
//                title = { Text("REMPLAZA TITULO AQUI") },
//            )
//        },
//    ){padding ->
//
//        // Organiza los elementos verticalmente (barra de pestañas + contenido).
//        Column(
//            modifier =
//                Modifier
//                    // Ocupa todo el tamaño disponible de la pantalla.
//                    .fillMaxSize()
//                    // Aplica el padding interno que genera Scaffold
//                    .padding(padding),
//        ) {
//            // TabRow dibuja las pestañas horizontales (como una barra superior con dos opciones).
//            TabRow(selectedTabIndex = selectedTabIndex) {
//                // Genera dinámicamente las pestañas a partir de la lista "tabs".
//                tabs.forEachIndexed { index, title ->
//
//                    // Cada pestaña muestra su título y cambia de color cuando está seleccionada.
//                    Tab(
//                        text = { Text(title) },
//                        selected = selectedTabIndex == index,
//                        onClick = { selectedTabIndex = index },
//                    )
//                }
//            }
//
//            // Dependiendo de la pestaña seleccionada, muestra una u otra pantalla.
//            /*when (selectedTabIndex) {
//                0 ->
//                    //Componentes
//                    RemplazaListContent(
//                        // Lista de Remplaza obtenida del ViewModel.
//                        remplazaList = uiState.remplazaList,
//                        // Indica si está cargando.
//                        isLoading = uiState.isLoading,
//                        // Mensaje de error (si lo hay).
//                        error = uiState.error,
//                        // Callback de navegación.
//                        onRemplazaClick = onRemplazaClick,
//                        // Parámetro agregado
//                        onRetry = { viewModel.loadRemplazaList() },
//                    )
//                1 -> SearchTab(onRemplazaClick = onRemplazaClick)
//            }*/
//        }
//    }
}
