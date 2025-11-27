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
import com.app.examen2025.presentation.screens.celebrity.CelebrityScreen

sealed class Screen(
    val route: String,
) {
    object Home : Screen("home")

    object Celebrity : Screen("celebrity/{name}") {
        fun createRoute(name: String) = "celebrity/${Uri.encode(name)}"
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
        startDestination = Screen.Celebrity.route,
        modifier = modifier,
    ) {
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
    }
}
