package br.sapiens.bellus_app.data.repository.implemetation.appointment

import br.sapiens.bellus_app.data.datasource.base.GetAppointmentByIdDataSource
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.data.repository.base.GetAppointmentByIdRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetAppointmentByIdRepositoryImpl @Inject constructor(
    private val dataSource: GetAppointmentByIdDataSource,
) : GetAppointmentByIdRepository {

    override suspend fun get(id: String): State<AppointmentDTO> {
        return try {
            when (val response: State<AppointmentDTO> = dataSource.get(id)) {
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