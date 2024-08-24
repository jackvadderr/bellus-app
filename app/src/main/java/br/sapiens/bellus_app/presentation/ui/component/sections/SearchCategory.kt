package br.sapiens.bellus_app.presentation.ui.component.sections

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.sapiens.bellus_app.R

@Composable
fun CategoryItem(
    imageRes: Int,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageRes),
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
fun CategoriesList(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
    ) {
        items(getCategories().size) { index ->
            val category = getCategories()[index]
            CategoryItem(
                imageRes = category.imageRes,
                title = category.title,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

data class Category(
    @DrawableRes val imageRes: Int,
    val title: String
)

fun getCategories(): List<Category> {
    return listOf(
        Category(imageRes = R.mipmap.barbearia, title = "Barbearias"),
        Category(imageRes = R.mipmap.barbearia, title = "Salões de Beleza"),
        Category(imageRes = R.mipmap.barbearia, title = "Saúde & Bem-estar"),
        Category(imageRes = R.mipmap.barbearia, title = "Depilação"),
        Category(imageRes = R.mipmap.barbearia, title = "Esteticistas"),
        Category(imageRes = R.mipmap.barbearia, title = "Manicure & Nail Designer"),
        Category(imageRes = R.mipmap.barbearia, title = "Sobrancelhas & Cílios"),
        Category(imageRes = R.mipmap.barbearia, title = "Make Up")
    )
}

