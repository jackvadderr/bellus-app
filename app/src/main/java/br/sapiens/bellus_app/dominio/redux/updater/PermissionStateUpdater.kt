package br.sapiens.bellus_app.dominio.redux.updater

import br.sapiens.bellus_app.dominio.model.event.PermissionEvent
import br.sapiens.bellus_app.dominio.model.state.PermissionState
import br.sapiens.bellus_app.dominio.redux.ApplicationState
import javax.inject.Inject

class PermissionStateUpdater @Inject constructor() {

    fun update(
        event: PermissionEvent,
        currentState: ApplicationState,
    ): ApplicationState {
        val newPermissionState: PermissionState = when (event) {
            is PermissionEvent.GalleryPermissionGranted -> {
                currentState.permissionState.copy(
                    isGalleryPermissionGranted = event.granted,
                    isRequestingPermission = false
                )
            }

//            is PermissionEvent.OpenGallery -> {
//                currentState.permissionState.copy(
////                    shouldOpenGallery = event.shouldOpen
//                )
//            }

            is PermissionEvent.RequestGalleryPermission -> {
                currentState.permissionState.copy(
                    isRequestingPermission = true
                )
            }

            is PermissionEvent.SaveURILocal -> {
                currentState.permissionState.copy(
                    localUri = event.uri
                )
            }

            is PermissionEvent.SaveDownloadUrl -> {
                currentState.permissionState.copy(
                    downloadUrl = event.downloadUrl
                )
            }
        }
        return currentState.copy(
            permissionState = newPermissionState
        )
    }
}