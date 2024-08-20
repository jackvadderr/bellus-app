package br.sapiens.bellus_app.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.R


@Composable
fun CategorySection() {
    val categories = listOf(
        Pair("Saúde e bem-estar", R.mipmap.bem_estar),
        Pair("Barbearias", R.mipmap.barbearia),
        Pair("Beleza", R.mipmap.beleza),
        Pair("Estetica", R.mipmap.estetica),
        Pair("Maquiagem", R.mipmap.maquiagem)
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            CategoryItem(category.first, category.second)
        }
    }
}

@Composable
fun CategoryItem(category: String, imageRes: Int) {
    val itemWidth = 80.dp
    val itemHeight = 100.dp
    val imageSize = 60.dp
    val textSize = MaterialTheme.typography.body2
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(itemWidth)
            .height(itemHeight)
            .clickable { /* Ação ao clicar */ }
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = category,
            modifier = Modifier
                .size(imageSize)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray)
        )
        Text(text = category, style = textSize)
    }
}
