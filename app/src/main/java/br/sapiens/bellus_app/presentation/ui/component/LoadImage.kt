package br.sapiens.bellus_app.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import br.sapiens.bellus_app.dominio.sdk.storage.CoilImageLoaderProvider
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.Transformation

@Composable
fun LoadImage(
    url: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null,
    crossfade: Boolean = true,
    placeholder: Int? = null,
    error: Int? = null,
    transformations: List<Transformation> = emptyList(),
    scale: Scale = Scale.FILL,
    size: coil.size.Size = coil.size.Size.ORIGINAL
) {
    val context = LocalContext.current
    val imageLoader = CoilImageLoaderProvider.getInstance(context).imageLoader

    val requestBuilder = ImageRequest.Builder(context)
        .data(url)
        .crossfade(crossfade)
        .scale(scale)
        .size(size)
        .transformations(transformations)

    placeholder?.let { requestBuilder.placeholder(it) }
    error?.let { requestBuilder.error(it) }

    val painter = rememberAsyncImagePainter(
        requestBuilder.build(),
        imageLoader = imageLoader
    )

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}