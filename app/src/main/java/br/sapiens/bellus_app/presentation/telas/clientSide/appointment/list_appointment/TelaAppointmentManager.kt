package br.sapiens.bellus_app.presentation.telas.clientSide.appointment.list_appointment

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
import br.sapiens.bellus_app.presentation.viewmodels.ManagerAppointmentViewModel
import br.sapiens.bellus_app.utils.calculateHourDifference
import br.sapiens.bellus_app.utils.getDayOfMonth
import br.sapiens.bellus_app.utils.getDayOfWeek
import br.sapiens.bellus_app.utils.getMonth

@Composable
fun TelaAppointmentManager(
    viewModel: ManagerAppointmentViewModel,
) {
    val viewState by viewModel.uiState.collectAsState()
    var qtdAppointment by remember { mutableIntStateOf(0) }
    val namesEstablishments = remember { mutableStateListOf<String>() }
    val servicePrices = remember { mutableStateListOf<Float>() }
    val serviceTime = remember { mutableStateListOf<String>() }

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
            is ManagerAppointmentViewModel.ViewState.Loading -> {
                WhyDontFuckingLoading()
            }

            is ManagerAppointmentViewModel.ViewState.LoadedAppointments -> {
                val appointments: List<AppointmentDTO> =
                    (viewState as ManagerAppointmentViewModel.ViewState.LoadedAppointments).appointments
                qtdAppointment = appointments.size
                if (appointments.isNotEmpty()) {

                    Log.d("TelaAppointmentManager", appointments.toString())
                    LaunchedEffect(appointments) {
                        namesEstablishments.clear()
                        servicePrices.clear()
                        appointments.forEach { appointment ->
                            val name = viewModel.getEstablishmentsName(appointment.establishmentId)
                            namesEstablishments.add(name)

                            val price = viewModel.getServicePrice(appointment.serviceId)
                            servicePrices.add(price)
                        }
                    }
                    if (namesEstablishments.size == appointments.size && servicePrices.size == appointments.size) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        ) {
                            itemsIndexed(appointments) { index, appointment ->
                                val name = namesEstablishments[index]
                                val price = servicePrices[index]
                                AgendamentoCard(
                                    day = getDayOfMonth(appointment.scheduled_date).toString(),
                                    weekDay = getDayOfWeek(appointment.scheduled_date),
                                    month = getMonth(appointment.scheduled_date),
                                    name = name,
                                    time =
                                    calculateHourDifference(
                                        appointment.scheduled_date,
                                        15
                                    ),
                                    price = price,
                                    status = appointment.statusRequest,
                                )
                                Spacer(modifier = Modifier.height(7.dp))
                            }
                        }
                    }

                } else {
                    EmptyState("Não há nenhum agendamento futuro", "Faça um agora mesmo!")
                }


            }

            is ManagerAppointmentViewModel.ViewState.Error -> {
                Text(
                    text = "Erro ao carregar os agendamentos",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Red
                )
            }
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




