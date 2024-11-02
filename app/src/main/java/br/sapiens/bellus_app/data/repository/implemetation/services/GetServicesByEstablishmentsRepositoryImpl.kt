package br.sapiens.bellus_app.data.repository.implemetation.services

import android.util.Log
import br.sapiens.bellus_app.data.datasource.base.GetServicesByEstablishmentDataSource
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.data.repository.base.GetServicesByEstablishmentsRepository
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject

class GetServicesByEstablishmentsRepositoryImpl @Inject constructor(
    private val getServicesByEstablishmentDataSource: GetServicesByEstablishmentDataSource,
) : GetServicesByEstablishmentsRepository {

    override suspend fun getServicesByEstablishment(id: String): State<List<ServiceDTO>> {
        Log.d(
            "GetServicesByEstablishmentsRepositoryImpl",
            "Fetching services for establishment ID: $id"
        )
        return try {
            when (val response =
                getServicesByEstablishmentDataSource.getServicesByEstablishment(id)) {
                is State.Success -> {
                    val establishments = response.data
                    Log.d(
                        "GetServicesByEstablishmentsRepositoryImpl",
                        "Successfully fetched services: $establishments"
                    )
                    State.Success(establishments)
                }

                is State.Error -> {
                    Log.d(
                        "GetServicesByEstablishmentsRepositoryImpl",
                        "Error fetching services: ${response.exception}"
                    )

                    response
                }
            }
        } catch (e: Exception) {
            Log.e(
                "GetServicesByEstablishmentsRepositoryImpl",
                "Exception occurred while fetching services: $e"
            )
            State.Error(e)
        }
    }
}