package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.data.datasource.entity.Horario
import br.sapiens.bellus_app.data.datasource.entity.HorarioFuncionamento

@Composable
fun WorkingHoursSection(horarioFuncionamento: HorarioFuncionamento) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Horário de Funcionamento",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            HorarioDiaSemana("Segunda-feira", horarioFuncionamento.segunda_feira)
            HorarioDiaSemana("Terça-feira", horarioFuncionamento.terca_feira)
            HorarioDiaSemana("Quarta-feira", horarioFuncionamento.quarta_feira)
            HorarioDiaSemana("Quinta-feira", horarioFuncionamento.quinta_feira)
            HorarioDiaSemana("Sexta-feira", horarioFuncionamento.sexta_feira)
            HorarioDiaSemana("Sábado", horarioFuncionamento.sabado)
            HorarioDiaSemana("Domingo", horarioFuncionamento.domingo)
        }
    }
}

//@Composable
//fun WorkingHoursSectionParceiro(horarioFuncionamento: HorarioFuncionamento) {
//    Column(modifier = Modifier.fillMaxWidth()) {
//        Text(
//            text = "Horário de Funcionamento",
//            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
//            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
//        )
//
//        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
//            HorarioDiaSemana("Segunda-feira", horarioFuncionamento.segunda_feira)
//            HorarioDiaSemana("Terça-feira", horarioFuncionamento.terca_feira)
//            HorarioDiaSemana("Quarta-feira", horarioFuncionamento.quarta_feira)
//            HorarioDiaSemana("Quinta-feira", horarioFuncionamento.quinta_feira)
//            HorarioDiaSemana("Sexta-feira", horarioFuncionamento.sexta_feira)
//            HorarioDiaSemana("Sábado", horarioFuncionamento.sabado)
//            HorarioDiaSemana("Domingo", horarioFuncionamento.domingo)
//        }
//    }
//}


@Composable
fun HorarioDiaSemana(dia: String, horario: Horario?) {
    val textoHorario = if (horario != null) {
        "${horario.abertura} - ${horario.fechamento}"
    } else {
        "Fechado"
    }

    Text(
        text = "$dia      $textoHorario",
        style = MaterialTheme.typography.bodyMedium
    )
}