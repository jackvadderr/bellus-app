package br.sapiens.bellus_app.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.sapiens.bellus_app.presentation.ui.theme.MarronNaoSei

@Composable
fun WhyDontFuckingLoading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(Modifier.align(Alignment.Center), color = MarronNaoSei)
    }
}

