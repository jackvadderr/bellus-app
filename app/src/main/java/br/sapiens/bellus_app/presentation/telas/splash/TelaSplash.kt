package br.sapiens.bellus_app.presentation.telas.splash


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
import br.sapiens.bellus_app.presentation.viewmodels.SplashViewModel


@Composable
fun TelaSplash(
    viewModel: SplashViewModel,
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val isSplashShow by viewModel.isSplashShow.collectAsState()

    if (!isSplashShow) {
        navigateToLogin()
    } else {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#1B2634"))),
            contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SplashArtAnimada(onAnimationComplete = navigateToLogin)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelaSplash() {
    val viewModel = SplashViewModel()
    TelaSplash(
        viewModel = viewModel,
        navigateToLogin = {},
        navigateToHome = {}
    )
}