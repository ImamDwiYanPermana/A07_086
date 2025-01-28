package com.example.ujian_terapi.navigation



import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ujian_terapi.ui.view.DestinasiDetail
import com.example.ujian_terapi.ui.view.DestinasiEntryPasien
import com.example.ujian_terapi.ui.view.DestinasiHome
import com.example.ujian_terapi.ui.view.DestinasiUpdate
import com.example.ujian_terapi.ui.view.DetailPasienView
import com.example.ujian_terapi.ui.view.EntryPasienScreen
import com.example.ujian_terapi.ui.view.HomeScreenPasien
import com.example.ujian_terapi.ui.view.JenisTerapiView.DestinasiDetailJenisTerapi
import com.example.ujian_terapi.ui.view.JenisTerapiView.DestinasiEntryJenisTerapi
import com.example.ujian_terapi.ui.view.JenisTerapiView.DestinasiHomeJenisTerapi
import com.example.ujian_terapi.ui.view.JenisTerapiView.DestinasiUpdateJenisTerapi
import com.example.ujian_terapi.ui.view.JenisTerapiView.DetailJenisTerapiView
import com.example.ujian_terapi.ui.view.JenisTerapiView.EntryJenisTerapiScreen
import com.example.ujian_terapi.ui.view.JenisTerapiView.HomeScreenJenisTerapi
import com.example.ujian_terapi.ui.view.JenisTerapiView.UpdateJenisTerapiView
import com.example.ujian_terapi.ui.view.TerapisView.DestinasiDetailTerapis
import com.example.ujian_terapi.ui.view.TerapisView.DestinasiEntryTerapis
import com.example.ujian_terapi.ui.view.TerapisView.DestinasiHomeTerapis
import com.example.ujian_terapi.ui.view.TerapisView.DestinasiUpdateTerapis
import com.example.ujian_terapi.ui.view.TerapisView.EntryTerapisScreen
import com.example.ujian_terapi.ui.view.TerapisView.HomeScreenTerapis
import com.example.ujian_terapi.ui.view.TerapisView.TerapisDetailScreen
import com.example.ujian_terapi.ui.view.TerapisView.TerapisUpdateScreen
import com.example.ujian_terapi.ui.view.UpdatePasienView

@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        // Home Pasien
        composable(DestinasiHome.route) {
            HomeScreenPasien(
                navigateToItemEntry = { navController.navigate(DestinasiEntryPasien.route) },
                onDetailClick = { idPasien ->
                    navController.navigate("${DestinasiDetail.route}/$idPasien")
                },
                navigateToPasien = { navController.navigate(DestinasiHome.route) },
                navigateToTerapis = { navController.navigate(DestinasiHomeTerapis.route) },
                navigateToJenisTerapi = { navController.navigate(DestinasiHomeJenisTerapi.route) },
                navigateToSesiTerapi = {},
                navigateToUpdatePasien = { idPasien ->
                    navController.navigate("${DestinasiUpdate.route}/$idPasien")
                }
            )
        }

        composable(DestinasiHomeJenisTerapi.route) {
            HomeScreenJenisTerapi(
                navigateToPasien = { navController.navigate(DestinasiHome.route) },
                navigateToTerapis = { navController.navigate(DestinasiHomeTerapis.route) },
                navigateToJenisTerapi = { navController.navigate(DestinasiHomeJenisTerapi.route) },
                navigateToSesiTerapi = {  },
                onDetailClick = {idJenisTerapi ->
                    navController.navigate("${DestinasiDetailJenisTerapi.route}/$idJenisTerapi")},
                navigateToAddJenisTerapi = { navController.navigate(DestinasiEntryJenisTerapi.route) },
                navigateToDetailJenisTerapi = { id ->
                    navController.navigate("${DestinasiDetailJenisTerapi.route}/$id")
                },
                navigateToUpdateTerapis = { id ->
                    navController.navigate("${DestinasiUpdateTerapis.route}/$id")
                }
            )
        }


        // Entry Pasien
        composable(DestinasiEntryPasien.route) {
            EntryPasienScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        // Detail Pasien
        composable(
            DestinasiDetail.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetail.idpasien) { type = NavType.IntType })
        ) { backStackEntry ->
            val idPasien = backStackEntry.arguments?.getInt(DestinasiDetail.idpasien) ?: -1
            DetailPasienView(
                idPasien = idPasien,
                navigateBack = { navController.popBackStack() },
                onEditClick = { idPasien ->
                    navController.navigate("${DestinasiUpdate.route}/$idPasien")
                }
            )
        }

        // Update Pasien
        composable(
            DestinasiUpdate.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdate.idpasien) { type = NavType.IntType })
        ) { backStackEntry ->
            val idPasien = backStackEntry.arguments?.getInt(DestinasiUpdate.idpasien)
            idPasien?.let {
                UpdatePasienView(
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }

        // Home Terapis
        composable(DestinasiHomeTerapis.route) {
            HomeScreenTerapis(
                navigateToTerapisEntry = { navController.navigate(DestinasiEntryTerapis.route) },
                onDetailClick = { idTerapis ->
                    navController.navigate("${DestinasiDetailTerapis.route}/$idTerapis")
                },
                navigateToPasien = { navController.navigate(DestinasiHome.route) },
                navigateToTerapis = { navController.navigate(DestinasiHomeTerapis.route) },
                navigateToJenisTerapi = { navController.navigate(DestinasiHomeJenisTerapi.route) },
                navigateToSesiTerapi = {},
                navigateToUpdateTerapis = { idTerapis ->
                    navController.navigate("${DestinasiUpdateTerapis.route}/$idTerapis")
                }
            )
        }

        // Entry Terapis
        composable(DestinasiEntryTerapis.route) {
            EntryTerapisScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        // Detail Terapis
        composable(
            DestinasiDetailTerapis.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetailTerapis.idTerapis) { type = NavType.IntType })
        ) { backStackEntry ->
            val idTerapis = backStackEntry.arguments?.getInt(DestinasiDetailTerapis.idTerapis) ?: -1
            TerapisDetailScreen(
                idTerapis = idTerapis,
                navigateBack = { navController.popBackStack() },
                onEditClick = { idTerapis ->
                    navController.navigate("${DestinasiUpdateTerapis.route}/$idTerapis")
                }
            )
        }

        // Update Terapis
        composable(
            DestinasiUpdateTerapis.routeWithArg,
            arguments = listOf(navArgument(DestinasiUpdateTerapis.idTerapis) { type = NavType.IntType })
        ) { backStackEntry ->
            val idTerapis = backStackEntry.arguments?.getInt(DestinasiUpdateTerapis.idTerapis)
            idTerapis?.let {
                TerapisUpdateScreen(
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }

        // Home Jenis Terapi


        // Entry Jenis Terapi
        composable(DestinasiEntryJenisTerapi.route) {
            EntryJenisTerapiScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        // Detail Jenis Terapi
        composable(
            DestinasiDetailJenisTerapi.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetailJenisTerapi.idJenisTerapi) { type = NavType.IntType })
        ) { backStackEntry ->
            val idJenisTerapi = backStackEntry.arguments?.getInt(DestinasiDetailJenisTerapi.idJenisTerapi) ?: -1
            DetailJenisTerapiView(
                idJenisTerapi = idJenisTerapi,
                navigateBack = { navController.popBackStack() },
                onEditClick = { idJenisTerapi ->
                    navController.navigate("${DestinasiUpdateJenisTerapi.route}/$idJenisTerapi")
                }
            )
        }

        // Update Jenis Terapi
        composable(
            DestinasiUpdateJenisTerapi.routeWithArg,
            arguments = listOf(navArgument(DestinasiUpdateJenisTerapi.idJenisTerapi) { type = NavType.IntType })
        ) { backStackEntry ->
            val idJenisTerapi = backStackEntry.arguments?.getInt(DestinasiUpdateJenisTerapi.idJenisTerapi)
            idJenisTerapi?.let {
                UpdateJenisTerapiView(
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }
    }
}