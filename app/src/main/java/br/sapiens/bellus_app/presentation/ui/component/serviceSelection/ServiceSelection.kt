package br.sapiens.bellus_app.presentation.ui.component.serviceSelection

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.presentation.ui.theme.MarronNaoSei

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
fun ReviewsTab() {
    Column(modifier = Modifier.padding(16.dp)) {
        // Overall rating
        Text(
            text = "Avaliações",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rating",
                tint = Color(0xFFFFD700), // Gold color for stars
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "5,0", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "(100)", style = MaterialTheme.typography.bodyMedium)
        }

        // List of reviews
        val reviews = listOf(
            Review("Wesley Santos", "Sáb, 30 de mar. de 2024 às 23:10", 5, "Perfeito!"),
            Review("Silva Neto", "Sáb, 25 de mar. de 2024 às 15:10", 4, "Gostei muito."),
            Review("Roberto Souza", "Seg., 18 de mar. de 2024 às 15:10", 5, "Perfeito!"),
        )

        reviews.forEach { review ->
            ReviewItem(review = review)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "User",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = review.username, style = MaterialTheme.typography.bodyLarge)
            Text(text = review.date, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(review.rating) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = review.comment, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

data class Review(
    val username: String,
    val date: String,
    val rating: Int,
    val comment: String
)

@Composable
fun PortfolioTab() {
    val images = listOf(
        R.mipmap.barbearia,
        R.mipmap.barbearia,
        R.mipmap.barbearia,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        images.forEach { imageRes ->
            PortfolioImage(imageRes)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PortfolioImage(@DrawableRes imageRes: Int) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "Portfolio Image",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun AboutTab() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.mipmap.imagem_mapa),
            contentDescription = "Location Map",
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
            text = "A Navalha Dourada é uma barbearia que combina o charme clássico com serviços de ponta...",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TeamSection()

        Spacer(modifier = Modifier.height(16.dp))

        ContactSection()

        Spacer(modifier = Modifier.height(16.dp))

        WorkingHoursSection()

        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
fun TeamMemberItem(imageRes: Int, name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
        )
    }
}

@Composable
fun TeamSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Membros da Equipe",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TeamMemberItem(imageRes = R.drawable.ic_baseline_person_24, name = "Jorge Marcos")
            TeamMemberItem(imageRes = R.drawable.ic_baseline_person_24, name = "Gabriel Peixoto")
            TeamMemberItem(imageRes = R.drawable.ic_baseline_person_24, name = "Lucas Silva")
        }
    }
}

@Composable
fun ContactSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Contato",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "Phone",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "(69) 99587-8506",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { /* Handle call */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E685A))
            ) {
                Text(text = "Ligar")
            }
        }
    }
}

@Composable
fun WorkingHoursSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Horário de Funcionamento",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = "Segunda-feira       8:00 - 18:00", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Terça-feira             8:00 - 18:00", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Quarta-feira          8:00 - 18:00", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Quinta-feira           8:00 - 18:00", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Sexta-feira           8:00 - 18:00", style = MaterialTheme.typography.bodyMedium)
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
        Button(onClick = onScheduleClick, colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MarronNaoSei,
            contentColor = Color.White
        )) {
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
            onClick = { /* Handle back click */ },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 230.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            ServiceInfoSection()

            var selectedTabIndex by remember { mutableStateOf(0) }
            val tabTitles = listOf("Serviços", "Avaliações", "Portfólio", "Sobre")
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
                contentColor = MarronNaoSei
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> ServiceList()
                1 -> ReviewsTab()
                2 -> PortfolioTab()
                3 -> AboutTab()
            }
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

