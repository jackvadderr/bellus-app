package br.sapiens.bellus_app.presentation.telas.professionalSide

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
import br.sapiens.bellus_app.presentation.viewmodels.parceiroSide.ParceiroAppointmentViewModel
import br.sapiens.bellus_app.utils.formatDuration
import br.sapiens.bellus_app.utils.getDayOfMonth
import br.sapiens.bellus_app.utils.getDayOfWeek
import br.sapiens.bellus_app.utils.getHour
import br.sapiens.bellus_app.utils.getMonth
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun TelaAgendamentoParceiro(
    viewModel: ParceiroAppointmentViewModel,
    navigateToSelectedAppointmentParceiro: () -> Unit,
) {
    val viewState by viewModel.uiState.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }

    val refreshData = {
        viewModel.triggerEvent(ParceiroAppointmentViewModel.ViewEvent.Loading)
    }

    LaunchedEffect(viewState) {
        isRefreshing = viewState is ParceiroAppointmentViewModel.ViewState.Loading
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = refreshData
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                SectionTitle("Agendamentos")
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                AppointmentsSection(
                    viewState,
                    viewModel,
                    navigateToSelectedAppointmentParceiro,
                    listOf("Pendente", "Aceito")
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
            item {
                SectionTitle("Anteriores")
                AppointmentsSection(
                    viewState,
                    viewModel,
                    navigateToSelectedAppointmentParceiro,
                    listOf("Rejeitado", "Concluído", "Cancelado")
                )
            }
        }
    }
}

@Composable
fun AppointmentsSection(
    viewState: ParceiroAppointmentViewModel.ViewState,
    viewModel: ParceiroAppointmentViewModel,
    navigateToSelectedAppointmentParceiro: () -> Unit,
    statusFilter: List<String>
) {
    when (viewState) {
        is ParceiroAppointmentViewModel.ViewState.Loading -> WhyDontFuckingLoading()
        is ParceiroAppointmentViewModel.ViewState.LoadedAppointments -> {
            val appointments = viewState.appointments.filter { it.statusRequest in statusFilter }
            if (appointments.isNotEmpty()) {
                AppointmentsList(appointments, viewModel, navigateToSelectedAppointmentParceiro)
            } else {
                EmptyState("Não há nenhum agendamento futuro", "Faça um agora mesmo!")
            }
        }

        ParceiroAppointmentViewModel.ViewState.Error -> {
            Text(
                text = "Erro ao carregar os agendamentos",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Red
            )
        }

        else -> {}
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
    viewState: ParceiroAppointmentViewModel.ViewState,
    viewModel: ParceiroAppointmentViewModel,
    navigateToSelectedAppointmentParceiro: () -> Unit
) {
    when (viewState) {
        is ParceiroAppointmentViewModel.ViewState.Loading -> WhyDontFuckingLoading()
        is ParceiroAppointmentViewModel.ViewState.LoadedAppointments -> {
            val appointments = viewState.appointments
            if (appointments.isNotEmpty()) {
                AppointmentsList(appointments, viewModel, navigateToSelectedAppointmentParceiro)
            } else {
                EmptyState("Não há nenhum agendamento futuro", "Faça um agora mesmo!")
            }
        }

        ParceiroAppointmentViewModel.ViewState.Error -> {
            Text(
                text = "Erro ao carregar os agendamentos",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Red
            )
        }

        else -> {}
    }
}

@Composable
fun AppointmentsList(
    appointments: List<AppointmentDTO>,
    viewModel: ParceiroAppointmentViewModel,
    navigateToSelectedAppointmentParceiro: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        itemsIndexed(appointments) { index, appointment ->
            AppointmentItem(appointment, viewModel, navigateToSelectedAppointmentParceiro)
            Spacer(modifier = Modifier.height(7.dp))
        }
    }
}

@Composable
fun AppointmentItem(
    appointment: AppointmentDTO,
    viewModel: ParceiroAppointmentViewModel,
    navigateToSelectedAppointmentParceiro: () -> Unit
) {
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
            establishmentName = viewModel.getEstablishmentsName(appointment.establishmentId)
            serviceInfo = viewModel.getServicePrice(appointment.serviceId)!!
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
        scheduleHour = getHour(appointment.scheduled_date).toString(),
        onClick = {
            viewModel.sendAppointmentIdToStore(appointment.id)
            navigateToSelectedAppointmentParceiro()
        }
    )
}