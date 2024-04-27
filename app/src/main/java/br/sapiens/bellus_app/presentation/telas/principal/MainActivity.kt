package br.sapiens.bellus_app.presentation.telas.principal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.sapiens.bellus_app.BellusApp
import br.sapiens.bellus_app.presentation.navegation.NavGraph
import br.sapiens.bellus_app.presentation.telas.home.NavControllerProvider
import br.sapiens.bellus_app.presentation.viewmodels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var app: BellusApp

    @Inject
    lateinit var splashViewModel: SplashViewModel

    @Inject
    lateinit var navControllerProvider: NavControllerProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavGraph()
        }
    }
}

