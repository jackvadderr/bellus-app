package br.sapiens.bellus_app.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomSnackBar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    message: String,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = modifier.align(Alignment.BottomCenter),
            snackbar = {
                Surface(
                    shadowElevation = 4.dp,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Snackbar(
                        snackbarData = it,
                        containerColor = Color(0xFF2D2D2D)
                    )
                }
            }
        )
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar(
                message = message,
                actionLabel = "OK",
                duration = SnackbarDuration.Short
            )
        }
    }

}

@Preview
@Composable
fun PreviewCustomSnackBar() {
    val snackbarHostState = remember { SnackbarHostState() }

    CustomSnackBar(
        snackbarHostState = snackbarHostState,
        message = "Teste"
    )

}