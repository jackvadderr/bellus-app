package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.review

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.model.ReviewsDetails

@Composable
fun ReviewsTab(itemsReviewsDetails: List<ReviewsDetails>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Avaliações",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rating",
                tint = Color(0xFFFFD700), // Gold color for stars
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "5,0", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "(100)", style = MaterialTheme.typography.bodyMedium)
        }

        itemsReviewsDetails.forEach { review ->
            ReviewItem(review = review)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}