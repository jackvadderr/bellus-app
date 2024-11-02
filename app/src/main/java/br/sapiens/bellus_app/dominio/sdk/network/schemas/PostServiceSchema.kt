package br.sapiens.bellus_app.dominio.sdk.network.schemas

import br.sapiens.bellus_app.presentation.ui.model.Duration
import kotlinx.serialization.Serializable

@Serializable
data class PostServiceSchema(
    val name: String,
    val description: String,
    val price: Float,
    val duration: Duration,
    val establishment_id: String,
)

