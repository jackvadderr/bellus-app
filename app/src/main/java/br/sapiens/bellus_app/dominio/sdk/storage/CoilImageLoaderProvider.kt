package br.sapiens.bellus_app.dominio.sdk.storage

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.request.CachePolicy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoilImageLoaderProvider @Inject constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: CoilImageLoaderProvider? = null
        private const val TAG = "CoilImageLoader"

        fun getInstance(context: Context): CoilImageLoaderProvider {
            Log.d(TAG, "getInstance called")
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: CoilImageLoaderProvider(context).also {
                    INSTANCE = it
                    Log.d(TAG, "New instance created")
                }
            }
        }

        @Composable
        @JvmStatic
        fun get(): CoilImageLoaderProvider {
            Log.d(TAG, "get (Composable) called")
            return getInstance(LocalContext.current)
        }

        @VisibleForTesting
        fun clearInstance() {
            Log.d(TAG, "clearInstance called")
            INSTANCE = null
        }
    }

    private val okHttpClient: OkHttpClient by lazy {
        Log.d(TAG, "okHttpClient initialized")
        OkHttpClient.Builder().build()
    }

    val imageLoader: ImageLoader by lazy {
        Log.d(TAG, "imageLoader initialized")
        ImageLoader.Builder(context)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .okHttpClient(okHttpClient)
            .build()
    }

    suspend fun clearMemoryCache() {
        Log.d(TAG, "clearMemoryCache called")
        withContext(Dispatchers.IO) {
            imageLoader.memoryCache?.clear()
            Log.d(TAG, "Memory cache cleared")
        }
    }

    @OptIn(ExperimentalCoilApi::class)
    suspend fun clearDiskCache() {
        Log.d(TAG, "clearDiskCache called")
        withContext(Dispatchers.IO) {
            imageLoader.diskCache?.clear()
            Log.d(TAG, "Disk cache cleared")
        }
    }
}