package br.sapiens.bellus_app.data.datasource.entity

import kotlinx.serialization.Serializable

@Serializable
data class ReviewsDTO(
    val id: String,
    val user_id: String,
    val establishment_id: String,
    val rating: Int,
    val comment: String,
    val created_at: String,
    val updated_at: String,
)