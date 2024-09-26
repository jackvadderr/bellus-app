package br.sapiens.bellus_app.data.datasource.entity

import kotlinx.serialization.Serializable

@Serializable
data class ReviewsDTO(
    val id: String,
    val name: String,
    val user_id: String,
    val establishment_id: String,
    val rating: Int, // TODO: O rating não deve ser obtido, deve ser calculado!!!!
    val comment: String,
    val created_at: String,
    val updated_at: String,
)

@Serializable
data class ReviewsSummary(
    val average_rating: Float,
    val total_reviews: Int,
)