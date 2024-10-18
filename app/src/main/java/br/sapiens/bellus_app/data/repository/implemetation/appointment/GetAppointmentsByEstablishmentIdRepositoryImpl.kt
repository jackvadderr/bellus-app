package br.sapiens.bellus_app.data.repository.implemetation.appointment

import br.sapiens.bellus_app.data.datasource.base.GetAppointmentsByEstablishmentIdDataSource
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.data.repository.base.GetAppointmentsByEstablishmentIdRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetAppointmentsByEstablishmentIdRepositoryImpl @Inject constructor(
    private val dataSource: GetAppointmentsByEstablishmentIdDataSource,
) : GetAppointmentsByEstablishmentIdRepository {

    override suspend fun get(id: String): State<List<AppointmentDTO>> {
        return try {
            when (val response: State<List<AppointmentDTO>> = dataSource.get(id)) {
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