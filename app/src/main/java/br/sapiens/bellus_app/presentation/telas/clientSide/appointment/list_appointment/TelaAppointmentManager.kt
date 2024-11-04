package br.sapiens.bellus_app.presentation.telas.clientSide.appointment.list_appointment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.presentation.ui.component.WhyDontFuckingLoading
import br.sapiens.bellus_app.presentation.ui.component.appointment.AgendamentoCard
import br.sapiens.bellus_app.presentation.ui.component.appointment.EmptyState
import br.sapiens.bellus_app.presentation.ui.model.Duration
import br.sapiens.bellus_app.presentation.viewmodels.ManagerAppointmentViewModel
import br.sapiens.bellus_app.utils.formatDuration
import br.sapiens.bellus_app.utils.getDayOfMonth
import br.sapiens.bellus_app.utils.getDayOfWeek
import br.sapiens.bellus_app.utils.getMonth
import kotlinx.coroutines.launch

@Composable
fun TelaAppointmentManager(viewModel: ManagerAppointmentViewModel) {
    val viewState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SectionTitle("Agendamentos")
        Spacer(modifier = Modifier.height(16.dp))
        AppointmentsSection(viewState, viewModel, listOf("Pendente", "Aceito"))
        Spacer(modifier = Modifier.height(32.dp))
        SectionTitle("Anteriores")
        AppointmentsSection(viewState, viewModel, listOf("Rejeitado", "Concluído", "Cancelado"))
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun AppointmentsSection(
    viewState: ManagerAppointmentViewModel.ViewState,
    viewModel: ManagerAppointmentViewModel,
    statusFilter: List<String>
) {
    when (viewState) {
        is ManagerAppointmentViewModel.ViewState.Loading -> WhyDontFuckingLoading()
        is ManagerAppointmentViewModel.ViewState.LoadedAppointments -> {
            val appointments = viewState.appointments.filter { it.statusRequest in statusFilter }
            if (appointments.isNotEmpty()) {
                AppointmentsList(appointments, viewModel)
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
}

@Composable
fun AppointmentsList(appointments: List<AppointmentDTO>, viewModel: ManagerAppointmentViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        items(appointments) { appointment ->
            AppointmentItem(appointment, viewModel)
            Spacer(modifier = Modifier.height(7.dp))
        }
    }
}

@Composable
fun AppointmentItem(appointment: AppointmentDTO, viewModel: ManagerAppointmentViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var establishmentName by remember { mutableStateOf("") }
    var serviceInfo by remember {
        mutableStateOf(
            ServiceDTO(
                "",
                "",
                "",
                Duration("Hour", 0.0f),
                0f,
                ""
            )
        )
    }
    LaunchedEffect(appointment) {
        coroutineScope.launch {
            establishmentName = viewModel.getEstablishmentName(appointment.establishmentId)
            serviceInfo = viewModel.getServiceInfo(appointment.serviceId)
        }
    }
    AgendamentoCard(
        day = getDayOfMonth(appointment.scheduled_date).toString(),
        weekDay = getDayOfWeek(appointment.scheduled_date),
        month = getMonth(appointment.scheduled_date),
        name = establishmentName,
        time = formatDuration(serviceInfo.duration),
        price = serviceInfo.price,
        status = appointment.statusRequest,
    )
}

