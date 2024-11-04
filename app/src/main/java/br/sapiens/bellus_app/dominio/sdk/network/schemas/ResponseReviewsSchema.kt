package br.sapiens.bellus_app.dominio.sdk.network.schemas

import kotlinx.serialization.Serializable

@Serializable
data class ResponseReviewsSchema(
    val user_id: String,
    val rating: Int,
    val comment: String,
    val id: String,
    val name: String,
    val establishment_id: String,
    val created_at: String,
    val updated_at: String
)

//{
//    "rating": 2,
//    "user_id": "OCM515mUvNadSimHMAHXWypAA3Y2",
//    "comment": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus elementum magna turpis, id lobortis risus luctus a. In sed ipsum eu dui eleifend mattis ac non metus. Pellentesque et semper neque. Aliquam erat volutpat.",
//    "establishment_id": "yuuCoe5AD6Mv6AOgZkdN"
//}