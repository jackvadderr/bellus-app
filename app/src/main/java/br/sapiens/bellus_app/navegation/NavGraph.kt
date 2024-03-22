package br.sapiens.bellus_app.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.sapiens.bellus_app.telas.home.TelaNavegationBar
import br.sapiens.bellus_app.telas.login.TelaLogin
import br.sapiens.bellus_app.telas.splash.TelaSplash

@Composable
fun NavGraph(startDestination: String = RotasDestinos.SPLASH_ROUTE) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(RotasDestinos.SPLASH_ROUTE) {
            TelaSplash(navController)
        }
        composable(RotasDestinos.HOME_ROUTE) {
            TelaNavegationBar(navController)
        }
        composable(RotasDestinos.LOGIN_ROUTE) {
            TelaLogin(navController)
        }
    }
}