package com.app.examen2025.presentation.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.examen2025.presentation.screens.celebrity.CelebrityScreen
import com.app.examen2025.presentation.screens.initial.InitialScreen
import com.app.examen2025.presentation.screens.initial.InitialViewModel
import com.app.examen2025.presentation.screens.sudoku.SudokuScreen
import com.google.gson.Gson

sealed class Screen(
    val route: String,
) {
    object Home : Screen("home")

    object Initial : Screen("initial") {
        fun createRoute() = "initial"
    }

    object Celebrity : Screen("celebrity/{name}") {
        fun createRoute(name: String): String {
            val encoded = Uri.encode(name)
            Log.d("ROUTES", "Navegando a celebrity con name=$name encoded=$encoded")
            return "celebrity/$encoded"
        }
    }

    object Sudoku : Screen("sudoku/{width}/{height}/{difficulty}") {
        fun createRoute(
            width: Int,
            height: Int,
            difficulty: String,
        ): String {
            val encodedDifficulty = Uri.encode(difficulty)
            Log.d("ROUTES", "Navegando a sudoku con width=$width height=$height difficulty=$difficulty encoded=$encodedDifficulty")
            return "sudoku/$width/$height/$encodedDifficulty"
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Initial.route,
        modifier = modifier,
    ) {
        composable(Screen.Initial.route) {
            Log.d("NAVIGATION", "Pantalla: Initial")

            val initialVm: InitialViewModel =
                androidx.hilt.navigation.compose
                    .hiltViewModel()
            val saved = initialVm.savedGame.collectAsState().value

            InitialScreen(
                onSaveClick = { w, h, diff ->
                    Log.d("NAVIGATION", "Initial -> Sudoku con w=$w h=$h diff=$diff")
                    navController.navigate(Screen.Sudoku.createRoute(w, h, diff))
                },
                hasSavedGame = saved != null,
                onRestoreLastGame = {
                    saved?.let { sg ->
                        try {
                            val json = Gson().toJson(sg)
                            Log.d("NavGraph", "onRestoreLastGame() restoring SavedGame: $json")
                        } catch (e: Exception) {
                            Log.d(
                                "NavGraph",
                                "onRestoreLastGame() restoring SavedGame summary: width=${sg.width} height=${sg.height} difficulty=${sg.difficulty}",
                            )
                        }
                        navController.navigate(Screen.Sudoku.createRoute(sg.width, sg.height, sg.difficulty))
                    }
                },
            )
        }

        composable(
            Screen.Celebrity.route,
            arguments = listOf(navArgument("name") { type = NavType.StringType }),
        ) { backStackEntry ->

            val encoded = backStackEntry.arguments?.getString("name")
            Log.d("NAVIGATION", "CelebrityScreen recibió encoded=$encoded")

            val name = encoded?.let { Uri.decode(it) } ?: "Michael Jordan"
            Log.d("NAVIGATION", "CelebrityScreen usando name=$name")

            CelebrityScreen(
                onBackClick = { navController.popBackStack() },
                name = name,
            )
        }

        composable(
            Screen.Sudoku.route,
            arguments =
                listOf(
                    navArgument("width") { type = NavType.IntType },
                    navArgument("height") { type = NavType.IntType },
                    navArgument("difficulty") { type = NavType.StringType },
                ),
        ) { backStackEntry ->

            val width = backStackEntry.arguments?.getInt("width") ?: 3
            val height = backStackEntry.arguments?.getInt("height") ?: 3
            val difficulty = backStackEntry.arguments?.getString("difficulty")

            Log.d("NAVIGATION", "SudokuScreen recibió width=$width height=$height difficulty=$difficulty")

            SudokuScreen(
                onSaveClick = { navController.popBackStack() },
                width = width,
                height = height,
                difficulty = difficulty ?: "easy",
            )
        }
    }
}
