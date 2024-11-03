package br.sapiens.bellus_app.dominio.model.state

data class PermissionState(
    val isGalleryPermissionGranted: Boolean = false,
//    val shouldOpenGallery: Boolean = false,
    val isRequestingPermission: Boolean = false,
    val localUri: String? = null,
    val downloadUrl: String? = null,
)