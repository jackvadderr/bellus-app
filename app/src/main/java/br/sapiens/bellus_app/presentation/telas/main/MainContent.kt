//package br.sapiens.bellus_app.presentation.telas.main
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.CompositionLocalProvider
//import androidx.compose.runtime.staticCompositionLocalOf
//import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
//import br.sapiens.bellus_app.presentation.navegation.NavGraph
//import br.sapiens.bellus_app.presentation.viewmodels.GalleryCallback
//import kotlinx.coroutines.CoroutineScope
//
//@Composable
//fun MainContent(
//    mainScope: CoroutineScope,
//    userProfileStore: UserProfileStore,
//    galleryCallback: GalleryCallback
//) {
//    CompositionLocalProvider(LocalCoroutineScope provides mainScope) {
//        NavGraph(
//            userProfileStore = userProfileStore,
//            galleryCallback = galleryCallback
//        )
//    }
//}
//
//val LocalCoroutineScope = staticCompositionLocalOf<CoroutineScope> {
//    error("No CoroutineScope provided")
//}
