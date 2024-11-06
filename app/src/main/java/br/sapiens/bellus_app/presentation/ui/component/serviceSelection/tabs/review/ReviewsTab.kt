package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.review

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.presentation.ui.component.GeralTextField
import br.sapiens.bellus_app.presentation.ui.model.ReviewsDetails

@Composable
fun ReviewsTab(
    itemsReviewsDetails: List<ReviewsDetails>,
    averagedReviewsDetails: Float,
    totalReviewsDetails: Int,
    onSubmitReview: (String, Float) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
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
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = averagedReviewsDetails.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "(${totalReviewsDetails})",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        item {
            CommentDialogButton(onSubmitReview = onSubmitReview)
        }
        itemsIndexed(itemsReviewsDetails) { index, review ->
            ReviewItem(review = review)
            if (index < itemsReviewsDetails.lastIndex) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
fun ParceiroReviewsTab(
    itemsReviewsDetails: List<ReviewsDetails>,
    averagedReviewsDetails: Float,
    totalReviewsDetails: Int,
) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
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
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = averagedReviewsDetails.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "(${totalReviewsDetails})",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        itemsIndexed(itemsReviewsDetails) { index, review ->
            ReviewItem(review = review)
            if (index < itemsReviewsDetails.lastIndex) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
fun CommentDialogButton(onSubmitReview: (String, Float) -> Unit) {
    val showDialog = remember { mutableStateOf(false) }
    val comment = remember { mutableStateOf("") }
    val rating = remember { mutableStateOf(0.0f) }

    Button(onClick = { showDialog.value = true }) {
        Text("Avalie o estabelecimento")
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Adicionar comentário!") },
            text = {
                Column {
                    GeralTextField(
                        value = comment.value,
                        onValueChange = { comment.value = it },
                        placeholder = "Comment",
                        modifier = Modifier.height(56.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    StarRating(
                        rating = rating.value,
                        onRatingChanged = { rating.value = it }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onSubmitReview(comment.value, rating.value)
                        showDialog.value = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun StarRating(rating: Float, onRatingChanged: (Float) -> Unit) {
    Row {
        for (i in 1..5) {
            val starColor = if (i <= rating) Color(0xFFFFD700) else Color.Gray
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star $i",
                tint = starColor,
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
                    .clickable { onRatingChanged(i.toFloat()) }
            )
        }
    }
}