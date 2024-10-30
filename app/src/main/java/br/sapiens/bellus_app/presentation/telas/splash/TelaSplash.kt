package br.sapiens.bellus_app.presentation.telas.splash


import android.util.Log
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.dominio.model.state.UserProfileType
import br.sapiens.bellus_app.presentation.viewmodels.SplashViewModel
import br.sapiens.bellus_app.utils.login.EstadoAutenticacao
import kotlinx.coroutines.delay


@Composable
fun TelaSplash(
    viewModel: SplashViewModel,
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToParceiroProfile: () -> Unit,
) {
    val isSplashShow by viewModel.isSplashShow.collectAsState()

    val viewState by viewModel.uiState.collectAsState()

    if (!isSplashShow) {
        when (val currentState = viewState) {
            is SplashViewModel.ViewState.AuthState -> {
                val currentAuthState: EstadoAutenticacao = currentState.authState
                Log.d("TelaSplash", "currentAuthState: $currentAuthState")
                if (currentAuthState == EstadoAutenticacao.AUTENTICADO) {
                    //
                } else if (currentAuthState == EstadoAutenticacao.NAO_AUTENTICADO) {
                    Log.d("TelaSplash", "Navigating to Login")
                    navigateToLogin()
                }
            }

            is SplashViewModel.ViewState.ProfileType -> {
                val currentProfileType = currentState.profileType
                Log.d("TelaSplash", "currentProfileType: $currentProfileType")
                if (currentProfileType == UserProfileType.CLIENT) {
                    Log.d("TelaSplash", "Navigating to Home")
                    navigateToHome()
                } else if (currentProfileType == UserProfileType.PROFESSIONAL) {
                    Log.d("TelaSplash", "Navigating to Parceiro Profile")
                    navigateToParceiroProfile()
                }
            }

            else -> {
                Log.d("TelaSplash", "Unhandled state: $currentState")
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
