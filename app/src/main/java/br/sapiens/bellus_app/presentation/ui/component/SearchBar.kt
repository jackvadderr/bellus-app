package br.sapiens.bellus_app.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar() {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var items = remember {
        mutableStateListOf(
            "Barbeiro",
            "Cabeleireiro",
        )
    }

    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = text,
        onQueryChange = { text = it },
        onSearch = {
            items.add(text)
            active = false
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text("Buscar em Todo o Bellus") },
        leadingIcon = { Icon(modifier = Modifier.clickable { text = "" }, imageVector = Icons.Default.Search, contentDescription = "Abrir campo de pesquisa") },
        trailingIcon = {
            if (active) {
                Icon(modifier = Modifier.clickable { if (text.isNotEmpty()) {text = ""} else {active = false} }, imageVector = Icons.Default.Close, contentDescription = "Fechar campo de pesquisa")
            }
        }
    ) {
        items.forEach {
            Row(modifier = Modifier.padding(all = 14.dp)) {
                Icon(modifier = Modifier.padding(end = 10.dp), imageVector = Icons.Default.History, contentDescription = "Histórico de pesquisa")
                Text(text = it)
            }

        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewSearchBar() {
    CustomSearchBar()
}
