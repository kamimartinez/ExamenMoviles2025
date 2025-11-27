package com.app.pokedexapp.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.examen2025.presentation.screens.Initial.InitialScreen
import com.app.examen2025.presentation.screens.Sudoku.SudokuScreen
import com.app.examen2025.presentation.screens.celebrity.CelebrityScreen

sealed class Screen(
    val route: String,
) {
    object Home : Screen("home")

    object Initial : Screen("initial") {
        fun createRoute() = "initial"
    }

    object Celebrity : Screen("celebrity/{name}") {
        fun createRoute(name: String) = "celebrity/${Uri.encode(name)}"
    }

    object Sudoku : Screen("sudoku/{width}/{height}/{difficulty}") {
        fun createRoute(
            width: Int,
            height: Int,
            difficulty: String,
        ) = "sudoku/$width/$height/${Uri.encode(difficulty)}"
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
            InitialScreen(onSaveClick = { w, h, diff ->
                navController.navigate(Screen.Sudoku.createRoute(w, h, diff))
            })
        }

        composable(
            Screen.Celebrity.route,
            arguments = listOf(navArgument("name") { type = NavType.StringType }),
        ) { backStackEntry ->
            val encoded = backStackEntry.arguments?.getString("name")
            val name = encoded?.let { Uri.decode(it) } ?: "Michael Jordan"
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
            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: "easy"
            SudokuScreen(
                onSaveClick = { navController.popBackStack() },
                width = width,
                height = height,
                difficulty = difficulty,
            )
        }
    }
}
