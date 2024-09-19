package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.review

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
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
fun ReviewItem(review: ReviewsDetails) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "User",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = review.nome, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "Lembrar Colocar Data Aqui Amigo",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(review.rating) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = review.comment, style = MaterialTheme.typography.bodyMedium)
        }
    }
}