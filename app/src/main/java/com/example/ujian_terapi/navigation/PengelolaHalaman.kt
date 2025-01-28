package com.example.ujian_terapi.navigation



import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pertemuan12.ui.view.*

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { nim ->
                    navController.navigate("${DestinasiDetail.route.replace("{nim}", nim)}")
                }
            )
        }
        composable(DestinasiEntry.route) {
            EntryMhsScreen(navigateBack = {
                navController.navigate(DestinasiHome.route) {
                    popUpTo(DestinasiHome.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            route = DestinasiDetail.route,
            arguments = listOf(navArgument(DestinasiDetail.nimArg) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val nimArg = backStackEntry.arguments?.getString(DestinasiDetail.nimArg)
            DetailScreen(
                nim = nimArg ?: "",
                navigateBack = { navController.navigateUp() },
                navigateToEdit = { nim ->
                    navController.navigate("${DestinasiEdit.route.replace("{nim}", nim)}")
                }
            )
        }

        composable(
            route = DestinasiEdit.route,
            arguments = listOf(navArgument("nim") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val nimArg = backStackEntry.arguments?.getString("nim")
            EditScreen(
                nim = nimArg ?: "",
                navigateBack = { navController.navigateUp() },
                onNavigateBack = {
                    navController.navigate("${DestinasiDetail.route.replace("{nim}", nimArg ?: "")}") {
                        popUpTo(DestinasiEdit.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}