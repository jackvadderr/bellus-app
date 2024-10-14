package br.sapiens.bellus_app.data.repository.implemetation.appointment

import br.sapiens.bellus_app.data.datasource.base.PostAppointmentDataSource
import br.sapiens.bellus_app.data.datasource.entity.PostAppointmentDTO
import br.sapiens.bellus_app.data.repository.base.PostAppointmentRepository
import br.sapiens.bellus_app.dominio.sdk.network.schemas.RequestAppointmentSchema
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class PostAppointmentRepositoryImpl @Inject constructor(
    private val dataSource: PostAppointmentDataSource,
) : PostAppointmentRepository {

    override suspend fun post(schema: RequestAppointmentSchema): State<PostAppointmentDTO> {
        return try {
            when (val response =
                dataSource.post(schema)) {
                is State.Success -> {
                    val establishments = response.data
                    State.Success(establishments)
                }

                is State.Error -> response
            }
        } catch (e: Exception) {
            State.Error(e)
        }
    }
}