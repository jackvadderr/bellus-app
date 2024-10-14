package br.sapiens.bellus_app.data.repository.implemetation.appointment

import br.sapiens.bellus_app.data.datasource.base.GetAppointmentsByClientIdDataSource
import br.sapiens.bellus_app.data.datasource.entity.GetAppointmentDTO
import br.sapiens.bellus_app.data.repository.base.GetAppointmentsByClientIdRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetAppointmentsByClientIdRepositoryImpl @Inject constructor(
    private val dataSource: GetAppointmentsByClientIdDataSource,
) : GetAppointmentsByClientIdRepository {

    override suspend fun get(id: String): State<List<GetAppointmentDTO>> {
        return try {
            when (val response: State<List<GetAppointmentDTO>> = dataSource.get(id)) {
                is State.Success -> {
                    val categories = response.data
                    State.Success(categories)
                }

                is State.Error -> response
            }
        } catch (e: Exception) {
            State.Error(e)
        }
    }
}