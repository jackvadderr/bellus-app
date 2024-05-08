package br.sapiens.bellus_app.presentation.ui.component

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ShowToast(messagem: String, context: Context = LocalContext.current) {
    Toast.makeText(context, messagem, Toast.LENGTH_SHORT).show()
}

@Preview
@Composable
fun PreviewShowToast() {
    ShowToast(messagem = "Hello World! Estou testando!")
    ShowToast(messagem = "Hello World! Estou testando!")
    ShowToast(messagem = "Hello World! Estou testando!")
    ShowToast(messagem = "Hello World! Estou testando!")
    ShowToast(messagem = "Hello World! Estou testando!")
    ShowToast(messagem = "Hello World! Estou testando!")
    ShowToast(messagem = "Hello World! Estou testando!")
    ShowToast(messagem = "Hello World! Estou testando!")
    ShowToast(messagem = "Hello World! Estou testando!")
    ShowToast(messagem = "Hello World! Estou testando!")
}