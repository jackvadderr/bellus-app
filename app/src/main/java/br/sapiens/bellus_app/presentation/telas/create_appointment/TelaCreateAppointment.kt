package br.sapiens.bellus_app.presentation.telas.create_appointment

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.sapiens.bellus_app.presentation.ui.component.appointment.CalendarView
import br.sapiens.bellus_app.presentation.ui.component.appointment.Footer
import br.sapiens.bellus_app.presentation.ui.component.appointment.Header
import br.sapiens.bellus_app.presentation.ui.component.appointment.ServiceSummary
import br.sapiens.bellus_app.presentation.ui.component.appointment.TimeSlotSelector
import br.sapiens.bellus_app.presentation.ui.model.Duration
import br.sapiens.bellus_app.presentation.ui.model.ServicePost
import br.sapiens.bellus_app.presentation.viewmodels.CreateAppointmentViewModel

@Composable
fun TelaCreateAppointment(
    viewModel: CreateAppointmentViewModel,
    navigateToBack: () -> Unit,
    navigateToManagerAppointments: () -> Unit,
) {
    var selectedTimeSlot by remember {
        mutableStateOf("")
    }
    var selectedDate by remember {
        mutableLongStateOf(System.currentTimeMillis())
    }

    val servicePost by remember { // TODO: Implementar no redux support for appointment
        mutableStateOf(ServicePost("Corte de cabelo", Duration("hour", 1.5f), 50f))
    }

    LaunchedEffect(selectedDate, selectedTimeSlot) {
        viewModel.selectedDate = selectedDate
        viewModel.selectedTimeSlot = selectedTimeSlot
        viewModel.servicePost = servicePost
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Header(navigateToBack)
        CalendarView { date -> selectedDate = date }
        TimeSlotSelector { timeSlot -> selectedTimeSlot = timeSlot }

        ServiceSummary(servicePost)
        Spacer(modifier = Modifier.weight(1f))
        Footer(
            onClick = {
                Log.d("TelaCreateAppointment", "Só para ter certeza que o botão foi apertado")
                viewModel.createAppointment()
                navigateToManagerAppointments()
            }
        )
    }
}