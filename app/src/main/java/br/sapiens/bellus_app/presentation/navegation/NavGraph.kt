package br.sapiens.bellus_app.presentation.navegation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.sapiens.bellus_app.presentation.telas.barra_navegation.BottomNavigation
import br.sapiens.bellus_app.presentation.telas.home.TelaMarketplace
import br.sapiens.bellus_app.presentation.telas.home.TelaPesquisa
import br.sapiens.bellus_app.presentation.telas.login.TelaCadastro
import br.sapiens.bellus_app.presentation.telas.login.TelaLoginCredenciais
import br.sapiens.bellus_app.presentation.telas.login.TelaSocialLogin
import br.sapiens.bellus_app.presentation.telas.splash.TelaSplash
import br.sapiens.bellus_app.presentation.ui.component.CustomTopBar
import br.sapiens.bellus_app.presentation.viewmodels.TelaNavegationBarViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavGraph(startDestination: String = RotasDestinos.Splash.rota) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rotaAtual = navBackStackEntry?.destination?.route
    val telaNavegationviewModel: TelaNavegationBarViewModel = viewModel()
    val selectedItem = telaNavegationviewModel.selectedItem.observeAsState()

    val deveExibirBarraNavegacao: (String?) -> Boolean = {
        rota ->
        rota != RotasDestinos.Splash.rota &&
        rota != RotasDestinos.LoginSocial.rota &&
        rota != RotasDestinos.LoginCredencial.rota &&
        rota != RotasDestinos.Cadastro.rota
    }

    val rotasSemPadding = listOf(
        RotasDestinos.Splash.rota
    )

    val modifier = if (rotaAtual in rotasSemPadding) {
        Modifier
    } else {
        Modifier.padding(top = 60.dp, bottom = 65.dp)
    }

    Scaffold(
        topBar = {
            if (deveExibirBarraNavegacao(rotaAtual)) {
                CustomTopBar()
            }
        },
        bottomBar = {
            if (deveExibirBarraNavegacao(rotaAtual)) {
                BottomNavigation(navController, selectedItem.value)
            }
        }
    )
    { _ ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier
        ) {
            /* ######################
             *  SPLASH ART AMIGO
             * ######################
             */
            composable(RotasDestinos.Splash.rota) {
                TelaSplash(
                    hiltViewModel(),
                    navigateToHome = {
                        navController.navigate(
                            route = RotasDestinos.Home.rota,
                        ) { popUpTo(RotasDestinos.Splash.rota) { inclusive = true} }
                    },
                    navigateToLogin = {
                        navController.navigate(
                            route = RotasDestinos.LoginSocial.rota,
                        ) { popUpTo(RotasDestinos.Splash.rota) { inclusive = true} }
                    }
                )
            }
            /* ######################
             *  AREA GERAL DE LOGIN AMIGO
             * ######################
             */
            composable(RotasDestinos.LoginSocial.rota) {
                TelaSocialLogin(
                    hiltViewModel(),
                    navigateToLoginCredencial = {
                        navController.navigate(
                            route = RotasDestinos.LoginCredencial.rota,
                        ) { popUpTo(RotasDestinos.LoginCredencial.rota) { inclusive = true} }
                    },
                    navigateToHome = {
                        navController.navigate(
                            route = RotasDestinos.Home.rota,
                        ) { popUpTo(RotasDestinos.LoginSocial.rota) { inclusive = true} }
                    }
                )
            }
            composable(RotasDestinos.LoginCredencial.rota) {
                TelaLoginCredenciais(
                    hiltViewModel(),
                    navigateToRegister = {
                        navController.navigate(
                            route = RotasDestinos.Cadastro.rota,
                        ) { popUpTo(RotasDestinos.LoginCredencial.rota) { inclusive = true} }
                    },
                    navigateToHome = {
                        navController.navigate(
                            route = RotasDestinos.Home.rota,
                        ) { popUpTo(RotasDestinos.LoginCredencial.rota) { inclusive = true} }
                    },
                )
            }
            composable(RotasDestinos.Cadastro.rota) {
                TelaCadastro(
                    hiltViewModel(),
                    navigateToBack = {
                        navController.navigate(
                            route = RotasDestinos.LoginSocial.rota
                        ) { popUpTo(RotasDestinos.LoginSocial.rota) { inclusive = true} }
                    }
                )
            }
            /* ######################
             *  MARKETPLACE AMIGÃO
             * ######################
             */
            // Tela Home
            composable(RotasDestinos.Home.rota) {
                TelaMarketplace(
                    hiltViewModel(),
                    navigateToSearch = {
                        navController.navigate(
                            route = RotasDestinos.Home.rota,
                        ) { popUpTo(RotasDestinos.Home.rota) { inclusive = true} }
                    },
                    navigateToProfile = {
                        navController.navigate(
                            route = RotasDestinos.Perfil.rota
                        ) { popUpTo(RotasDestinos.Home.rota) { inclusive = true} }
                    },
                )
            }
            /* ######################
             *   TELA DE PESQUISAS
             * ######################
             */
            // Tela Pesquisa
            composable(RotasDestinos.Pesquisar.rota) {
                TelaPesquisa(
                    hiltViewModel(),
                    navigateToSearch = {
                        navController.navigate(
                            route = RotasDestinos.Home.rota,
                        ) { popUpTo(RotasDestinos.Home.rota) { inclusive = true} }
                    },
                    navigateToProfile = {
                        navController.navigate(
                            route = RotasDestinos.Perfil.rota
                        ) { popUpTo(RotasDestinos.Home.rota) { inclusive = true} }
                    },
                )
            }
        }
    }
}