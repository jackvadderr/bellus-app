package br.sapiens.bellus_app.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import br.sapiens.bellus_app.dominio.sdk.network.schemas.RequestAppointmentSchema
import br.sapiens.bellus_app.dominio.usecase.appointment.PostAppointmentUseCase
import br.sapiens.bellus_app.presentation.ui.model.ServicePost
import br.sapiens.bellus_app.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class CreateAppointmentViewModel @Inject constructor(
    private val postAppointmentUseCase: PostAppointmentUseCase,
    private val storeUser: UserProfileStore,
    private val storeMarketplace: MarketplaceStore,
) : BaseViewModel<CreateAppointmentViewModel.ViewState, CreateAppointmentViewModel.ViewEvent>() {


    var selectedProfessional by mutableStateOf<String?>(null)
    var selectedDate by mutableStateOf<Long?>(null)
    var selectedTimeSlot by mutableStateOf<String?>(null)
    var servicePost by mutableStateOf<ServicePost?>(null)


    override fun createInitialState(): ViewState {
        Log.d("CreateAppointmentViewModel", "Creating initial state")
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        Log.d("CreateAppointmentViewModel", "Triggering event: $event")
        when (event) {
            is ViewEvent.CreateAppointment -> createAppointment()
        }
    }

    fun createAppointment() {
        viewModelScope.launch {
            Log.d("CreateAppointmentViewModel", "Iniciando createAppointment()")
            val userId: String? = storeUser.getCurrentUserId()
            Log.d("CreateAppointmentViewModel", "UserId: $userId")
            val establishmentId: String? = storeMarketplace.getEstablishmentItemId()
            val serviceId: String? = storeMarketplace.getCurrentServiceDetails()?.id

            Log.d(
                "CreateAppointmentViewModel",
                "UserId: $userId, EstablishmentId: $establishmentId, ServiceId: $serviceId"
            )

            // Validação das entradas para garantir que não são nulas
            val selectedDateMillis = selectedDate ?: return@launch
            val selectedTime = selectedTimeSlot ?: return@launch

            Log.d(
                "CreateAppointmentViewModel",
                "SelectedDateMillis: $selectedDateMillis, SelectedTime: $selectedTime"
            )

            if (!establishmentId.isNullOrEmpty() && !serviceId.isNullOrEmpty()) {
                // Use a função combineDateTime para combinar a data e a hora
                val formattedDateTime: String = combineDateTime(selectedDateMillis, selectedTime)
                Log.d("CreateAppointmentViewModel", "FormattedDateTime: $formattedDateTime")

                // Chamar o use case com o agendamento
                when (val result = postAppointmentUseCase.invoke(
                    userId?.let {
                        RequestAppointmentSchema(
                            user_id = it,
                            establishment_id = establishmentId,
                            service_id = serviceId,
                            scheduled_date = formattedDateTime,
                            status_request = "Pendente",
                            completion_date = "",
                        )
                    }
                )) {
                    is State.Success -> {
                        val appointment = result.data
                        Log.d(
                            "CreateAppointmentViewModel",
                            "Appointment created successfully: $appointment"
                        )
                        setState {
                            ViewState.CreateAppointment(appointment)
                        }
                    }

                    is State.Error -> {
                        Log.e(
                            "CreateAppointmentViewModel",
                            "Error creating appointment: ${result.exception}"
                        )
                        // Handle error case
                    }
                }
            } else {
                Log.w(
                    "CreateAppointmentViewModel",
                    "Invalid input data: userId, establishmentId, or serviceId is empty"
                )
            }
        }
    }

    private fun combineDateTime(dateMillis: Long, time: String): String {
        Log.d("CreateAppointmentViewModel", "Combining date and time: $dateMillis, $time")

        // Converter a data em milissegundos para um Instant
        val instant = Instant.fromEpochMilliseconds(dateMillis)

        // Converter o Instant para LocalDateTime no fuso horário UTC
        val localDateTime = instant.toLocalDateTime(TimeZone.UTC)

        // Extrair as horas e minutos do time (esperando no formato "HH:mm")
        val timeParts = time.split(":")
        val hour = timeParts[0].toInt()
        val minute = timeParts[1].toInt()

        // Combinar a data e o horário em um único LocalDateTime
        val combinedDateTime = LocalDateTime(
            year = localDateTime.year,
            monthNumber = localDateTime.monthNumber,
            dayOfMonth = localDateTime.dayOfMonth,
            hour = hour,
            minute = minute
        )

        // Formatar no padrão ISO 8601 com o fuso horário UTC
        val formattedDateTime = combinedDateTime.toInstant(TimeZone.UTC).toString()

        Log.d("CreateAppointmentViewModel", "Combined DateTime: $formattedDateTime")
        return formattedDateTime
    }


    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data class CreateAppointment(val post: AppointmentDTO) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data class CreateAppointment(val servicePost: ServicePost) : ViewEvent()
    }
}