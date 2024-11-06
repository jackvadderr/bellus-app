package br.sapiens.bellus_app.presentation.ui.component.serviceSelection.tabs.about

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.data.datasource.entity.EstadoEnum
import br.sapiens.bellus_app.data.datasource.entity.Horario
import br.sapiens.bellus_app.data.datasource.entity.HorarioFuncionamento
import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetail

@Composable
fun AboutTab(
    description: String,
    contatos: List<String>,
    horario_funcionamento: HorarioFuncionamento
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        // TODO: Aqui vamos pegar as cordenadas do estabelecimento e usar api do google maps
        Image(
            painter = painterResource(id = R.mipmap.imagem_mapa),
            contentDescription = "Localização do estabelecimento",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Sobre nós",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

//        TeamSection()

        Spacer(modifier = Modifier.height(16.dp))

        ContactSection(contatos)

        Spacer(modifier = Modifier.height(16.dp))

        WorkingHoursSection(horario_funcionamento)

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun AboutTabParceiro(
    establishmentDetail: EstablishmentDetail,
    onSaveClick: (EstablishmentDetail) -> Unit
) {
    var description by remember { mutableStateOf(establishmentDetail.description) }
    var contatos by remember { mutableStateOf(establishmentDetail.telefone.toMutableList()) }
    var horarioFuncionamento by remember { mutableStateOf(establishmentDetail.horario_funcionamento) }
    var name by remember { mutableStateOf(establishmentDetail.name) }
    var rua by remember { mutableStateOf(establishmentDetail.address.rua) }
    var numero by remember { mutableStateOf(establishmentDetail.address.numero) }
    var cidade by remember { mutableStateOf(establishmentDetail.address.cidade) }
    var estado by remember { mutableStateOf(establishmentDetail.address.estado) }
    var cep by remember { mutableStateOf(establishmentDetail.address.cep) }
    var cnjp by remember { mutableStateOf(establishmentDetail.cnjp) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.mipmap.imagem_mapa),
            contentDescription = "Localização do estabelecimento",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Sobre nós",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descrição") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome do estabelecimento") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = cnjp,
            onValueChange = { cnjp = it },
            label = { Text("CNPJ") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = rua,
            onValueChange = { rua = it },
            label = { Text("Rua") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = numero,
            onValueChange = { numero = it },
            label = { Text("Número") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = cidade,
            onValueChange = { cidade = it },
            label = { Text("Cidade") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = estado.name,
            onValueChange = { estado = EstadoEnum.valueOf(it) },
            label = { Text("Estado") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = cep,
            onValueChange = { cep = it },
            label = { Text("CEP") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Informações de contato",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            contatos.forEachIndexed { index, contato ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = contato,
                        onValueChange = { newValue ->
                            contatos = contatos.toMutableList().apply { set(index, newValue) }
                        },
                        label = { Text("Contato ${index + 1}") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            contatos = contatos.toMutableList().apply { removeAt(index) }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Remover")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Button(
                onClick = {
                    contatos = contatos.toMutableList().apply { add("") }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Adicionar novo contato")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        WorkingHoursSectionParceiro(horarioFuncionamento) { updatedHorario ->
            horarioFuncionamento = updatedHorario
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val updatedEstablishmentDetail = establishmentDetail.copy(
                    name = name,
                    cnjp = cnjp,
                    description = description,
                    telefone = contatos.filter { it.isNotBlank() },
                    horario_funcionamento = horarioFuncionamento,
                    address = establishmentDetail.address.copy(
                        rua = rua,
                        numero = numero,
                        cidade = cidade,
                        estado = estado,
                        cep = cep
                    )
                )
                onSaveClick(updatedEstablishmentDetail)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Salvar")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
fun WorkingHoursSectionParceiro(
    horarioFuncionamento: HorarioFuncionamento,
    onHorarioChange: (HorarioFuncionamento) -> Unit
) {
    val diasDaSemana = listOf(
        "Segunda-feira" to horarioFuncionamento.segunda_feira,
        "Terça-feira" to horarioFuncionamento.terca_feira,
        "Quarta-feira" to horarioFuncionamento.quarta_feira,
        "Quinta-feira" to horarioFuncionamento.quinta_feira,
        "Sexta-feira" to horarioFuncionamento.sexta_feira,
        "Sábado" to horarioFuncionamento.sabado,
        "Domingo" to horarioFuncionamento.domingo
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Horário de Funcionamento",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            diasDaSemana.forEach { (dia, horario) ->
                HorarioDiaSemanaEditavel(dia, horario) { newHorario ->
                    val updatedHorarioFuncionamento = when (dia) {
                        "Segunda-feira" -> horarioFuncionamento.copy(segunda_feira = newHorario)
                        "Terça-feira" -> horarioFuncionamento.copy(terca_feira = newHorario)
                        "Quarta-feira" -> horarioFuncionamento.copy(quarta_feira = newHorario)
                        "Quinta-feira" -> horarioFuncionamento.copy(quinta_feira = newHorario)
                        "Sexta-feira" -> horarioFuncionamento.copy(sexta_feira = newHorario)
                        "Sábado" -> horarioFuncionamento.copy(sabado = newHorario)
                        "Domingo" -> horarioFuncionamento.copy(domingo = newHorario)
                        else -> horarioFuncionamento
                    }
                    onHorarioChange(updatedHorarioFuncionamento)
                }
            }
        }
    }
}

@Composable
fun HorarioDiaSemanaEditavel(
    dia: String,
    horario: Horario?,
    onHorarioChange: (Horario?) -> Unit
) {
    val abertura = horario?.abertura ?: ""
    val fechamento = horario?.fechamento ?: ""

    Column {
        Text(text = dia, style = MaterialTheme.typography.bodyMedium)
        Row {
            TextField(
                value = abertura,
                onValueChange = { newAbertura ->
                    onHorarioChange(
                        Horario(
                            newAbertura,
                            fechamento
                        )
                    )
                },
                label = { Text("Abertura") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = fechamento,
                onValueChange = { newFechamento ->
                    onHorarioChange(
                        Horario(
                            abertura,
                            newFechamento
                        )
                    )
                },
                label = { Text("Fechamento") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}