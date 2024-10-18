package br.sapiens.bellus_app.presentation.ui.component.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.presentation.ui.theme.BlueNaoSei

@Composable
fun ProfileHeader(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(BlueNaoSei) // Fundo azul escuro
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.width(24.dp))
        // Ícone do perfil (representado como um círculo)
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_camera), // Use um ícone adequado
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(60.dp)
                .background(Color.Gray, CircleShape)
                .padding(8.dp),
            tint = Color.White
        )

//        Spacer(modifier = Modifier.width(16.dp)) // Espaço entre o ícone e o texto

        // Texto de saudação
        Text(
            text = "Olá, $name",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MenuItem(
    iconResId: Int,
    title: String,
    subtitle: String,
    hasNotification: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = title,
            modifier = Modifier.size(30.dp),
            tint = Color(0xFF8E734D)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            // Título do item
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            // Subtítulo do item
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
        if (hasNotification) {
            BadgedBox(
                badge = { Text("1") }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.seta_direita),
                    contentDescription = "Arrow",
                    tint = Color.Gray
                )
            }
        } else {
            Icon(
                painter = painterResource(id = R.drawable.seta_direita),
                contentDescription = "Arrow",
                tint = Color.Gray
            )
        }
    }
}

//@Composable
//fun ProfileScreen() {
//    Column {
////        ProfileHeader(name = "João Marcos")
//
//        MenuItem(
//            iconResId = R.drawable.bubble_chat,
//            title = "Conversas",
//            subtitle = "Meu histórico de conversas",
//            hasNotification = true
//        )
//        HorizontalDivider()
//        MenuItem(
//            iconResId = R.drawable.notificacao,
//            title = "Notificações",
//            subtitle = "Central de notificações",
//            hasNotification = true
//        )
//        HorizontalDivider()
//        MenuItem(
//            iconResId = R.drawable.diamante,
//            title = "Clube Bellus",
//            subtitle = "Meus benefícios exclusivos"
//        )
//        HorizontalDivider()
//        MenuItem(
//            iconResId = R.drawable.contorno_em_forma_de_coracao,
//            title = "Favoritos",
//            subtitle = "Meus locais favoritos"
//        )
//        HorizontalDivider()
//        MenuItem(
//            iconResId = R.drawable.arquivo,
//            title = "Dados da conta",
//            subtitle = "Minhas informações da conta"
//        )
//        HorizontalDivider()
//        MenuItem(
//            iconResId = R.drawable.endereco,
//            title = "Login de parceiro Bellus",
//            subtitle = "Faça aqui o login do seu perfil de estabelecimento"
//        )
//        HorizontalDivider()
//        MenuItem(
//            iconResId = R.drawable.ajuda,
//            title = "Ajuda",
//            subtitle = "Central de ajuda"
//        )
//        HorizontalDivider()
//        MenuItem(
//            iconResId = R.drawable.configuracoes,
//            title = "Configurações",
//            subtitle = "Central de configurações"
//        )
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun PreviewProfileScreen() {
//    ProfileScreen()
//}