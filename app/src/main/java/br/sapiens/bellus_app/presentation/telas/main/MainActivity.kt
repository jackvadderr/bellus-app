package br.sapiens.bellus_app.presentation.telas.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.sapiens.bellus_app.BellusApp
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import br.sapiens.bellus_app.presentation.navegation.NavGraph
import br.sapiens.bellus_app.presentation.telas.clientSide.home.NavControllerProvider
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var app: BellusApp

    @Inject
    lateinit var navControllerProvider: NavControllerProvider

    @Inject
    lateinit var userProfileStore: UserProfileStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )
        setContent {
            NavGraph(userProfileStore = userProfileStore)
        }
    }
}

