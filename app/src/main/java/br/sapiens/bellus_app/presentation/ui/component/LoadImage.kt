package br.sapiens.bellus_app.presentation.ui.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.dominio.sdk.storage.CoilImageLoaderProvider
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Scale
import coil.transform.Transformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(
    urls: List<String>,
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

    var loadedUrls by remember { mutableStateOf<List<String>?>(null) }

    LaunchedEffect(urls) {
        withContext(Dispatchers.IO) {
            if (urls.size > 1) {
                val loaded = urls.map { url ->
                    val request = ImageRequest.Builder(context)
                        .data(url)
                        .build()
                    val result = (imageLoader.execute(request) as SuccessResult).drawable
                    url
                }
                loadedUrls = loaded
            }
        }
    }
    if (loadedUrls == null) {
        // Fazer nada
    } else {
        val pagerState = rememberPagerState(pageCount = { loadedUrls!!.size })

        Box(
            modifier = modifier,
            contentAlignment = Alignment.BottomCenter
        ) {
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                LoadImage(
                    url = loadedUrls!![page],
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale,
                    contentDescription = contentDescription,
                    crossfade = crossfade,
                    placeholder = placeholder,
                    error = error,
                    transformations = transformations,
                    scale = scale,
                    size = size
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                loadedUrls!!.forEachIndexed { index, _ ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(12.dp)
                    ) {
                        Canvas(modifier = Modifier.size(12.dp)) {
                            drawCircle(
                                color = if (isSelected) Color.White else Color.Black,
                                radius = 6f
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ParceiroImageUpdateSlider(
    urls: List<String>,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null,
    crossfade: Boolean = true,
    placeholder: Int? = null,
    error: Int? = null,
    transformations: List<Transformation> = emptyList(),
    scale: Scale = Scale.FILL,
    size: coil.size.Size = coil.size.Size.ORIGINAL,
    onAddImageClick: () -> Unit,
    onDeleteImageClick: (String) -> Unit
) {
    val context = LocalContext.current
    val imageLoader = CoilImageLoaderProvider.getInstance(context).imageLoader

    var loadedUrls by remember { mutableStateOf<List<String>>(urls) }

    LaunchedEffect(urls) {
        withContext(Dispatchers.IO) {
            val loaded = urls.map { url ->
                val request = ImageRequest.Builder(context)
                    .data(url)
                    .build()
                val result = (imageLoader.execute(request) as SuccessResult).drawable
                url
            }
            loadedUrls = loaded
        }
    }

    if (loadedUrls.isEmpty()) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        Log.d("ParceiroImageUpdateSlider", "Adicionar imagem clicado!")
                        onAddImageClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("Adicionar Imagem", color = Color.Black)
            }
        }
    } else {
        val pagerState = rememberPagerState(pageCount = { loadedUrls.size + 1 }) // +1 para o botão

        Box(
            modifier = modifier,
            contentAlignment = Alignment.BottomCenter
        ) {
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                if (page < loadedUrls.size) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        LoadImage(
                            url = loadedUrls[page],
                            modifier = Modifier.fillMaxSize(),
                            contentScale = contentScale,
                            contentDescription = contentDescription,
                            crossfade = crossfade,
                            placeholder = placeholder,
                            error = error,
                            transformations = transformations,
                            scale = scale,
                            size = size
                        )
                        IconButton(
                            onClick = {
                                onDeleteImageClick(loadedUrls[page])
                                loadedUrls = loadedUrls.filterNot { it == loadedUrls[page] }
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.trash),
                                contentDescription = "Delete Image",
                                tint = Color.Red
                            )
                        }
                    }
                } else {
                    // Exibir botão para adicionar imagem
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                Log.d("ParceiroImageUpdateSlider", "Adicionar imagem clicado!")
                                onAddImageClick()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Adicionar Imagem", color = Color.Black)
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                loadedUrls.forEachIndexed { index, _ ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(12.dp)
                    ) {
                        Canvas(modifier = Modifier.size(12.dp)) {
                            drawCircle(
                                color = if (isSelected) Color.White else Color.Black,
                                radius = 6f
                            )
                        }
                    }
                }
                // Indicador para o botão de adicionar imagem
                val isSelected = pagerState.currentPage == loadedUrls.size
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(12.dp)
                ) {
                    Canvas(modifier = Modifier.size(12.dp)) {
                        drawCircle(
                            color = if (isSelected) Color.White else Color.Black,
                            radius = 6f
                        )
                    }
                }
            }
        }
    }
}