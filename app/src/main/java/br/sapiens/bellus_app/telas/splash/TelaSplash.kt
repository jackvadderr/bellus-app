package br.sapiens.bellus_app.telas.splash


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.sapiens.bellus_app.viewmodels.SplashViewModel


@Composable
fun TelaSplash(navController: NavHostController, viewModel: SplashViewModel) {
    val isSplashShow by viewModel.isSplashShow.collectAsState()

    if (!isSplashShow) {
        navController.navigate("home")
    } else {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#1B2634"))),
            contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SplashArtAnimada(navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelaSplash() {
    val navController = rememberNavController()
    val viewModel = SplashViewModel()
    TelaSplash(navController, viewModel)
}