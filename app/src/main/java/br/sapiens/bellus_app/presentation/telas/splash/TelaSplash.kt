package br.sapiens.bellus_app.presentation.telas.splash


import android.util.Log
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.presentation.viewmodels.SplashViewModel
import br.sapiens.bellus_app.utils.login.EstadoAutenticacao
import kotlinx.coroutines.delay


@Composable
fun TelaSplash(
    viewModel: SplashViewModel,
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToParceiroHome: () -> Unit
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(android.graphics.Color.parseColor("#1B2634"))),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SplashArtAnimada()
            }
        }
    }


}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun SplashArtAnimada() {
    Log.d("SplashArtAnimada", "Iniciando animação")

    val image = AnimatedImageVector.animatedVectorResource(R.drawable.avd_bellus)
    var scale by remember { mutableFloatStateOf(0f) }
    val atEnd by remember { mutableStateOf(true) }
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
        scale = 0f
        delay(1000)
        scale = 1.8f
    }

    if (isVisible) {
        Image(
            painter = rememberAnimatedVectorPainter(image, atEnd = atEnd),
            contentDescription = "Timer",
            modifier = Modifier
                .size(250.dp)
                .scale(scale)
                .clickable {
                    scale = if (scale == 0f) 0.85f else 0f
                },
            contentScale = ContentScale.Crop
        )
    }

    Log.d("SplashArtAnimada", "Finalizando animação")
}
