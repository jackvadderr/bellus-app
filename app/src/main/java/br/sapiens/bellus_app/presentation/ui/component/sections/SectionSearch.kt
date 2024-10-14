package br.sapiens.bellus_app.presentation.ui.component.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.presentation.ui.component.LoadImage
import kotlinx.coroutines.launch

@Composable
fun CategoryItem(
    number: Int,
    imageRes: String,
    title: String,
    modifier: Modifier = Modifier,
    marketplaceStore: MarketplaceStore,
    navigate: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                coroutineScope.launch {
                    marketplaceStore.setCurrentNumberCategory(number)
                }
                navigate()
            },
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadImage(
                url = imageRes,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                color = Color.Black,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun CategoriesList(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    marketplaceStore: MarketplaceStore,
    navigate: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
    ) {
        items(categories.size) { index ->
            CategoryItem(
                imageRes = categories[index].imageRes,
                title = categories[index].title,
                modifier = Modifier.fillMaxWidth(),
                number = categories[index].number,
                marketplaceStore = marketplaceStore,
                navigate = navigate
            )
        }
    }
}

data class Category(
//    @DrawableRes val imageRes: Int,
    val imageRes: String,
    val title: String,
    val number: Int,
)


