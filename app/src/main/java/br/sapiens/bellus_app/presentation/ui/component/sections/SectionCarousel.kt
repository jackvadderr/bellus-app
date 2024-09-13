package br.sapiens.bellus_app.presentation.ui.component.sections

import LoadImage
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.model.CategoryModel
import br.sapiens.bellus_app.presentation.ui.theme.BlueNaoSei

@Composable
fun CategorySection(categories: List<CategoryModel>) {

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .background(BlueNaoSei)
    ) {
        items(categories) { category ->
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(120.dp)
                    .padding(0.dp)
            ) {
                CategoryCard(category)
            }

        }
    }
}

@Composable
fun CategoryCard(category: CategoryModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable { /* Ação ao clicar */ }
    ) {
        LoadImage(
            url = category.imageResource,
            contentDescription = category.name,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = category.name,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewCategorySection() {
//    CategorySection(categories)
//}
