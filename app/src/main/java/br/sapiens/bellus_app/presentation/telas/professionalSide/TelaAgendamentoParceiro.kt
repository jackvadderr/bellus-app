package br.sapiens.bellus_app.presentation.telas.professionalSide

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.presentation.ui.component.WhyDontFuckingLoading
import br.sapiens.bellus_app.presentation.ui.component.appointment.AgendamentoCard
import br.sapiens.bellus_app.presentation.ui.component.appointment.EmptyState
import br.sapiens.bellus_app.presentation.ui.model.Duration
import br.sapiens.bellus_app.presentation.viewmodels.parceiroSide.ParceiroAppointmentViewModel
import br.sapiens.bellus_app.utils.formatDuration
import br.sapiens.bellus_app.utils.getDayOfMonth
import br.sapiens.bellus_app.utils.getDayOfWeek
import br.sapiens.bellus_app.utils.getMonth

@Composable
fun TelaAgendamentoParceiro(
    viewModel: ParceiroAppointmentViewModel,
    navigateToSelectedAppointmentParceiro: () -> Unit,
) {
    val viewState by viewModel.uiState.collectAsState()
    var qtdAppointment by remember { mutableIntStateOf(0) }
    val namesEstablishments = remember { mutableStateListOf<String>() }
    val servicePrices = remember { mutableStateListOf<Float>() }
    val serviceStatus = remember { mutableStateListOf<String>() }
    val serviceDurations = remember { mutableStateListOf<Duration>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Agendamentos",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Próximos (${qtdAppointment})",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        when (viewState) {
            is ParceiroAppointmentViewModel.ViewState.Loading -> {
                WhyDontFuckingLoading()
            }

            is ParceiroAppointmentViewModel.ViewState.LoadedAppointments -> {
                val appointments: List<AppointmentDTO> =
                    (viewState as ParceiroAppointmentViewModel.ViewState.LoadedAppointments).appointments
                qtdAppointment = appointments.size

                if (appointments.isNotEmpty()) {
                    Log.d("TelaAppointmentManager", appointments.toString())
                    LaunchedEffect(appointments) {
                        namesEstablishments.clear()
                        servicePrices.clear()
                        serviceStatus.clear()
                        // Aqui a gente carrega os dados que não podemos pegar de uma vez
                        // Acontece que temos uma lista de agendamentos
                        // Para cada elemento vamos verificar o nome e o preço
                        // Acho que seria melhor verificar por serviço
                        appointments.forEach { appointment ->
                            val name = viewModel.getEstablishmentsName(appointment.establishmentId)
                            namesEstablishments.add(name)

                            val dto = viewModel.getServicePrice(appointment.serviceId)
                            val price = dto?.price
                            servicePrices.add(price!!)
                            serviceDurations.add(dto.duration)

                        }
                    }
                    if (namesEstablishments.size == appointments.size &&
                        servicePrices.size == appointments.size &&
                        servicePrices.size == serviceDurations.size
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        ) {
//                            formatDuration(serviceDetail.duration)
                            itemsIndexed(appointments) { index, appointment ->
                                val name = namesEstablishments[index]
                                val price = servicePrices[index]
                                AgendamentoCard(
                                    day = getDayOfMonth(appointment.scheduled_date).toString(),
                                    weekDay = getDayOfWeek(appointment.scheduled_date),
                                    month = getMonth(appointment.scheduled_date),
                                    name = name,
                                    time = formatDuration(serviceDurations[index]),
                                    price = price,
                                    status = appointment.statusRequest,
                                    onClick = {
                                        viewModel.sendAppointmentIdToStore(appointment.id)
                                        navigateToSelectedAppointmentParceiro()
                                    }
                                )
                                Spacer(modifier = Modifier.height(7.dp))
                            }
                        }
                    }

                } else {
                    EmptyState("Não há nenhum agendamento futuro", "Faça um agora mesmo!")
                }
            }

            is ParceiroAppointmentViewModel.ViewState.UpdateAppointment -> {}
            ParceiroAppointmentViewModel.ViewState.Error -> {
                Text(
                    text = "Erro ao carregar os agendamentos",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Red
                )
            }

            is ParceiroAppointmentViewModel.ViewState.EstablishmentId -> {}
            is ParceiroAppointmentViewModel.ViewState.LoadedAppointment -> {}
        }


        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Anteriores",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        EmptyState("Não há agendamentos anteriores", "Seus agendamentos aparecerão aqui")
    }
}