package br.sapiens.bellus_app.presentation.telas.appointment.create_appointment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.presentation.ui.component.appointment.ProfissionalCard
import br.sapiens.bellus_app.presentation.ui.theme.MarronNaoSei
import br.sapiens.bellus_app.presentation.viewmodels.CreateAppointmentViewModel

@Composable
fun TelaSelectionProfissional(
    viewModel: CreateAppointmentViewModel,
    navigateToCreateAppointment: () -> Unit,
    navigateToBack: () -> Unit
) {
    var selectedProfessional by remember { mutableStateOf("Qualquer profissional") }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            IconButton(onClick = { navigateToBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MarronNaoSei
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Selecionar profissional",
                style = MaterialTheme.typography.labelMedium,
                color = MarronNaoSei
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            ProfissionalCard(
                nome = "Qualquer profissional",
                imageRes = R.mipmap.bem_estar,
                isSelected = selectedProfessional == "Qualquer profissional",
                onClick = { selectedProfessional = "Qualquer profissional" }
            )
            ProfissionalCard(
                nome = "Gabriel Peixoto",
                imageRes = R.mipmap.bem_estar,
                isSelected = selectedProfessional == "Gabriel Peixoto",
                onClick = { selectedProfessional = "Gabriel Peixoto" }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) { // TODO:Futuramente vamos pegar os profissionais do banco
            ProfissionalCard(
                nome = "Jorge Marcos",
                imageRes = R.mipmap.bem_estar,
                isSelected = selectedProfessional == "Jorge Marcos",
                onClick = { selectedProfessional = "Jorge Marcos" }
            )
            ProfissionalCard(
                nome = "Lucas da Silva",
                imageRes = R.mipmap.bem_estar,
                isSelected = selectedProfessional == "Lucas da Silva",
                onClick = { selectedProfessional = "Lucas da Silva" }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button( // TODO:Futuramente vamos pegar os profissionais do banco
            onClick = {
                viewModel.selectedProfessional = selectedProfessional
                navigateToCreateAppointment()
            },
            enabled = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(50.dp),
            colors = ButtonColors(
                contentColor = MarronNaoSei,
                containerColor = MarronNaoSei,
                disabledContainerColor = Color(0xFFAAAAAA),
                disabledContentColor = Color(0xFFAAAAAA)
            )
        ) {
            Text(
                text = "Seguinte",
                fontSize = 18.sp,
                color = Color.White
            )

        }
    }
}