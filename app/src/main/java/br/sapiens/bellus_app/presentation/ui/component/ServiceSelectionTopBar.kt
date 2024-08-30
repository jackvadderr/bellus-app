package br.sapiens.bellus_app.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceSelectionTopBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text("Tela de Seleção de Serviço") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            Column(horizontalAlignment = Alignment.End) {
                Text("5,0")
                Text("100 avaliações", style = MaterialTheme.typography.bodySmall)
            }
        }
    )
}

@Composable
fun ServiceInfoSection() {
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "A Navalha Dourada",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Rua João Pedro da Rocha, 1545, 76820-110, Porto Velho (RO)",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Aberto até 18:00",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}

@Composable
fun ServiceTabs(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("Serviços", "Avaliações", "Portfólio", "Sobre")
    TabRow(selectedTabIndex = selectedTabIndex) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) }
            )
        }
    }
}

@Composable
fun ServiceItem(serviceName: String, duration: String, price: String, onScheduleClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = serviceName, style = MaterialTheme.typography.titleSmall)
            Text(text = duration, style = MaterialTheme.typography.bodyMedium)
            Text(text = "a partir de $price", style = MaterialTheme.typography.bodyMedium)
        }
        Button(onClick = onScheduleClick) {
            Text(text = "Agendar")
        }
    }
}

@Composable
fun ServiceList() {
    Column {
        ServiceItem(serviceName = "Corte + Barba", duration = "35 minutos", price = "R$ 50,00") {
            // Handle button click
        }
        HorizontalDivider()
        ServiceItem(serviceName = "Barba", duration = "25 minutos", price = "R$ 25,00") {
            // Handle button click
        }
        HorizontalDivider()
        ServiceItem(serviceName = "Sobrancelha", duration = "10 minutos", price = "R$ 20,00") {
            // Handle button click
        }
        HorizontalDivider()
        ServiceItem(serviceName = "Completo", duration = "50 minutos", price = "R$ 80,00") {
            // Handle button click
        }
    }
}

@Composable
fun ServiceSelectionScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.mipmap.barbearia),
            contentDescription = "Barber Shop Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = {  },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 230.dp)  // Adjust padding to push content below the image
                .background(MaterialTheme.colorScheme.background)
        ) {
            Spacer(modifier = Modifier.height(8.dp)) // Add a bit of spacing if needed
            ServiceInfoSection()
            var selectedTabIndex by remember { mutableStateOf(0) }
            ServiceTabs(selectedTabIndex = selectedTabIndex, onTabSelected = { selectedTabIndex = it })
            ServiceList()
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ServiceSelectionScreenPreview() {
    MaterialTheme {
        ServiceSelectionScreen()
    }
}

