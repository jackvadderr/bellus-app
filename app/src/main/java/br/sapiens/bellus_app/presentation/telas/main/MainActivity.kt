package br.sapiens.bellus_app.presentation.telas.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import br.sapiens.bellus_app.BellusApp
import br.sapiens.bellus_app.dominio.model.event.PermissionEvent
import br.sapiens.bellus_app.dominio.redux.stores.PermissionStore
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import br.sapiens.bellus_app.presentation.navegation.NavGraph
import br.sapiens.bellus_app.presentation.telas.clientSide.home.NavControllerProvider
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var app: BellusApp

    @Inject
    lateinit var navControllerProvider: NavControllerProvider

    @Inject
    lateinit var userProfileStore: UserProfileStore


    @Inject
    lateinit var permissionStore: PermissionStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate: Inicio")
        FirebaseApp.initializeApp(this)
        Log.d("MainActivity", "onCreate: FirebaseApp initialized")
        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )
        Log.d("MainActivity", "onCreate: FirebaseAppCheck initialized")
        setContent {
            NavGraph(userProfileStore = userProfileStore)
        }
        Log.d("MainActivity", "onCreate: setContent called")
        lifecycleScope.launch {
            executeBackgroundTask()
        }
        Log.d("MainActivity", "onCreate: Background task launched")
    }

    @OptIn(FlowPreview::class)
    private suspend fun executeBackgroundTask() {
        Log.d("MainActivity", "executeBackgroundTask: Started")
        permissionStore.store.stateFlow.sample(1000).collect { state ->
            Log.d("MainActivity", "executeBackgroundTask: State collected: $state")
            if (state.permissionState.isRequestingPermission) {
                Log.d("MainActivity", "executeBackgroundTask: Requesting permission")
                if (state.permissionState.isGalleryPermissionGranted) {
                    Log.d("MainActivity", "executeBackgroundTask: Gallery permission granted")
                    openGallery()
                    permissionStore.dispatch(PermissionEvent.RequestGalleryPermission(false))
                } else {
                    permissionStore.dispatch(PermissionEvent.RequestGalleryPermission(true))
                }
            } else {
                requestGalleryPermission()
            }
        }
    }

    private fun requestGalleryPermission() {
        Log.d("MainActivity", "requestGalleryPermission: Checking permission")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("MainActivity", "requestGalleryPermission: Permission not granted, requesting")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_GALLERY_PERMISSION
            )
        } else {
            Log.d("MainActivity", "requestGalleryPermission: Permission already granted")
            lifecycleScope.launch {
                permissionStore.dispatch(PermissionEvent.GalleryPermissionGranted(true))
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(
            "MainActivity",
            "onRequestPermissionsResult: requestCode=$requestCode, grantResults=${grantResults.joinToString()}"
        )
        if (requestCode == REQUEST_GALLERY_PERMISSION) {
            val granted =
                grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            Log.d("MainActivity", "onRequestPermissionsResult: Permission granted=$granted")
            lifecycleScope.launch {
                permissionStore.dispatch(PermissionEvent.GalleryPermissionGranted(granted))
                if (granted) {
                    openGallery()
                } else {
                    Log.d("MainActivity", "onRequestPermissionsResult: Permission denied")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(
            "MainActivity",
            "onActivityResult: requestCode=$requestCode, resultCode=$resultCode, data=$data"
        )
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            Log.d("MainActivity", "onActivityResult: Selected image URI=$selectedImageUri")
            selectedImageUri?.let {
                lifecycleScope.launch {
                    permissionStore.dispatch(PermissionEvent.SaveURILocal(it.toString()))
                }
            }
        }
    }

    private fun openGallery() {
        Log.d("MainActivity", "openGallery: Opening gallery")
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    companion object {
        private const val REQUEST_GALLERY_PERMISSION = 1
        private const val REQUEST_GALLERY = 2
    }
}

