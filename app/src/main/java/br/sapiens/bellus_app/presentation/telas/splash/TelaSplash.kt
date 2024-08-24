package br.sapiens.bellus_app.presentation.telas.splash


import android.util.Log
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
import androidx.hilt.navigation.compose.hiltViewModel
import br.sapiens.bellus_app.presentation.viewmodels.SplashViewModel
import br.sapiens.bellus_app.utils.login.EstadoAutenticacao


@Composable
fun TelaSplash(
    viewModel: SplashViewModel,
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val isSplashShow by viewModel.isSplashShow.collectAsState()

    val state by viewModel.uiState.collectAsState()

    val authState = state.authState

    if (!isSplashShow) {
        if (authState == EstadoAutenticacao.AUTENTICADO) {
            Log.d("TelaSplash", "Autenticado")
            navigateToHome()
        } else if (authState == EstadoAutenticacao.NAO_AUTENTICADO) {
            Log.d("TelaSplash", "NÃO Autenticado")
            navigateToLogin()
        }
    } else {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#1B2634"))),
            contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SplashArtAnimada()
            }
        }
    }


}

//@Preview(showBackground = true)
//@Composable
//fun PreviewTelaSplash() {
//    TelaSplash(
//        viewModel = hiltViewModel(),
//        navigateToLogin = {},
//        navigateToHome = {}
//    )
//}