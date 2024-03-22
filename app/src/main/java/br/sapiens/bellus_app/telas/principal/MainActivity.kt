package br.sapiens.bellus_app.telas.principal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import br.sapiens.bellus_app.BellusApp
import br.sapiens.bellus_app.navegation.NavGraph
import br.sapiens.bellus_app.viewmodels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var app: BellusApp

    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isSplashShow by splashViewModel.isSplashShow.collectAsState()
            if(isSplashShow) {
                NavGraph(startDestination = "splash")
            } else {
                NavGraph(startDestination = "home")
            }
        }
    }
}

