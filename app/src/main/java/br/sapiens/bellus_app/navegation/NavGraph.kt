package br.sapiens.bellus_app.navegation

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.sapiens.bellus_app.telas.barra_navegation.BottomNavigation
import br.sapiens.bellus_app.telas.home.NavControllerProvider
import br.sapiens.bellus_app.telas.home.TelaMarketplace
import br.sapiens.bellus_app.telas.login.TelaLogin
import br.sapiens.bellus_app.telas.splash.TelaSplash
import br.sapiens.bellus_app.viewmodels.TelaNavegationBarViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NavGraph(startDestination: String = RotasDestinos.Splash.rota) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val TelaNavegationviewModel: TelaNavegationBarViewModel = viewModel()
    val selectedItem = TelaNavegationviewModel.selectedItem.observeAsState()

    Scaffold(
        bottomBar = {
            if (currentRoute != RotasDestinos.Splash.rota && currentRoute != RotasDestinos.Login.rota) {
                BottomNavigation(navController, selectedItem.value)
            }
        }
    ) { _ ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
        ) {
            // Tela Splash Art
            composable(RotasDestinos.Splash.rota) {
                TelaSplash(
                    hiltViewModel(),
                    navigateToHome = {
                        navController.navigate(
                            route = RotasDestinos.Home.rota,
                        )
                    },
                    navigateToLogin = {
                        navController.navigate(
                            route = RotasDestinos.Login.rota,
                        )
                    }
                )
            }
            // Tela Login
            composable(RotasDestinos.Login.rota) {
                TelaLogin(
                    hiltViewModel(),
                    navigateToRegister = {
                        navController.navigate(
                            route = RotasDestinos.Register.rota,
                        )
                    },
                    navigateToHome = {
                        navController.navigate(
                            route = RotasDestinos.Home.rota,
                        )
                    }
                )
            }
            // Tela Home
//            composable(RotasDestinos.Home.rota) {
//                TelaMarketplace(
//                    hiltViewModel(),
//                    navigateToSearch = {
//                        navController.navigate(
//                            route = RotasDestinos.Home.rota,
//                        )
//                    },
//                    navigateToProfile = {
//                        navController.navigate(
//                            route = RotasDestinos.Perfil.rota
//                        )
//                    },
//                )
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNavGraph() {
    val navControllerProvider = NavControllerProvider().apply { this.navController = rememberNavController() }
}
