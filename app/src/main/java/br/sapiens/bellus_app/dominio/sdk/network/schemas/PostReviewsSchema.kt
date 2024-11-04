package br.sapiens.bellus_app.dominio.sdk.network.schemas

import kotlinx.serialization.Serializable

@Serializable
data class PostReviewsSchema(
    val establishment_id: String,
    val user_id: String,
    val rating: Float,
    val comment: String,
)

//data class PostReviewsSchemaEncapsulation(
//    val id: String,
//    val schema: PostReviewsSchema
//)
