package br.sapiens.bellus_app.dominio.model.event

sealed class PermissionEvent {
    data class RequestGalleryPermission(val isRequest: Boolean) : PermissionEvent()
    data class GalleryPermissionGranted(val granted: Boolean) : PermissionEvent()

    //    data class OpenGallery(val shouldOpen: Boolean) : PermissionEvent()
    data class SaveURILocal(val uri: String) : PermissionEvent()
    data class SaveDownloadUrl(val downloadUrl: String) : PermissionEvent()
}