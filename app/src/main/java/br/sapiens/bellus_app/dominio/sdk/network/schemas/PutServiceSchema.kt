package br.sapiens.bellus_app.dominio.sdk.network.schemas

import br.sapiens.bellus_app.presentation.ui.model.Duration
import kotlinx.serialization.Serializable

@Serializable
data class PutServiceSchema(
    val name: String,
    val description: String,
    val price: Float,
    val duration: Duration,
    val establishment_id: String,
)


@Serializable
data class PutServiceSchemaEncapsulation(
    val id: String,
    val schema: PutServiceSchema,
)