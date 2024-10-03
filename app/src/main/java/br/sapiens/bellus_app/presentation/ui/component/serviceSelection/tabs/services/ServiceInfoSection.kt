package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.services

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.data.datasource.entity.EnderecoPartialModel
import br.sapiens.bellus_app.data.datasource.entity.Horario
import br.sapiens.bellus_app.data.datasource.entity.HorarioFuncionamento
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun ServiceInfoSection(
    name: String,
    endereco: EnderecoPartialModel,
    horarioFuncionamento: HorarioFuncionamento
) {
    val aberto = verificarHorarioFuncionamento(horarioFuncionamento)
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "${endereco.rua}, ${endereco.numero}, ${endereco.cep}, ${endereco.cidade} (${endereco.estado})",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = if (aberto) "Aberto agora" else "Fechado agora",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}


fun verificarHorarioFuncionamento(horarioFuncionamento: HorarioFuncionamento): Boolean {
    val agora = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    return when (agora.date.dayOfWeek) {
        DayOfWeek.MONDAY -> verificarIntervalo(horarioFuncionamento.segunda_feira, agora.time)
        DayOfWeek.TUESDAY -> verificarIntervalo(horarioFuncionamento.terca_feira, agora.time)
        DayOfWeek.WEDNESDAY -> verificarIntervalo(horarioFuncionamento.quarta_feira, agora.time)
        DayOfWeek.THURSDAY -> verificarIntervalo(horarioFuncionamento.quinta_feira, agora.time)
        DayOfWeek.FRIDAY -> verificarIntervalo(horarioFuncionamento.sexta_feira, agora.time)
        DayOfWeek.SATURDAY -> verificarIntervalo(horarioFuncionamento.sabado, agora.time)
        DayOfWeek.SUNDAY -> verificarIntervalo(horarioFuncionamento.domingo, agora.time)
        else -> false
    }
}

private fun verificarIntervalo(
    intervalo: Horario?,
    horaAtual: LocalTime
): Boolean {
    return intervalo?.let {
        val abertura = LocalTime.parse(it.abertura)
        val fechamento = LocalTime.parse(it.fechamento)

        // Comparação manual de horas e minutos
        (horaAtual.hour > abertura.hour || (horaAtual.hour == abertura.hour && horaAtual.minute >= abertura.minute)) &&
                (horaAtual.hour < fechamento.hour || (horaAtual.hour == fechamento.hour && horaAtual.minute <= fechamento.minute))
    } ?: false
}