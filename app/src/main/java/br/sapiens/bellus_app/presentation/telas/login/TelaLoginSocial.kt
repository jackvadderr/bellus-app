package br.sapiens.bellus_app.presentation.telas.login


import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.IntentSender.SendIntentException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startIntentSenderForResult
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.presentation.ui.component.CustomButton
import br.sapiens.bellus_app.presentation.viewmodels.LoginSocialViewModel
import br.sapiens.bellus_app.utils.getAndroidSDKVersion
import br.sapiens.bellus_app.utils.login.EstadoAutenticacao
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun TelaSocialLogin(
    viewModel: LoginSocialViewModel,
    navigateToLoginCredencial: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    val context: Context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF1D2B3D)), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .padding(10.dp)
                .background(Color(0xFF1D2B3D))
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.mipmap.bellus_imagem),
                contentDescription = "Bellus Login",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.padding(0.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(5.dp))
                if(getAndroidSDKVersion() >= 15) {
                    /*
                     * BOTÃO DO GOOGLE
                     */
                    CustomButton(onClick = {
                        coroutineScope.launch {
//                            viewModel.googleSign(context)
                        }
                    }, texto = "Entrar com Google")
                    Spacer(modifier = Modifier.padding(10.dp))
                }
                    /*
                     * BOTÃO LOGIN COM EMAIL E SENHA
                     */
                    CustomButton(onClick = {
                        navigateToLoginCredencial()
                    }, texto = "Entrar com credenciais")
                }
        }
    }
    if(state.loginState == EstadoAutenticacao.AUTENTICADO) {
        navigateToHome()
    }
}

