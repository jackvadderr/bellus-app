package br.sapiens.bellus_app.presentation.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import br.sapiens.bellus_app.presentation.ui.theme.BlueNaoSei

@SuppressLint("UnusedmaterialScaffoldPaddingParameter")
@Composable
fun TelaMarketplace(
) {
    Scaffold(
        topBar = {
            CustomTopBar()
        },
        content = {}
    )
}

@Composable
fun CustomTopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                "Bellus",
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { /* doSomething() */ }
            ) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(
                onClick = { /* doSomething() */ }
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
        },
        backgroundColor = BlueNaoSei
    )
}

@Preview(showBackground = true)
@Composable
fun TelaMarketplacePreview() {
    TelaMarketplace()
}