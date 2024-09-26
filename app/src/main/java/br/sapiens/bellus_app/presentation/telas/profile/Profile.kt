package br.sapiens.bellus_app.presentation.telas.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.presentation.ui.component.profile.MenuItem
import br.sapiens.bellus_app.presentation.viewmodels.MarketplaceViewModel


@Composable
fun Profile(
    viewModel: MarketplaceViewModel,
) {
    Column {
//        ProfileHeader(name = "João Marcos")

        MenuItem(
            iconResId = R.drawable.bubble_chat,
            title = "Conversas",
            subtitle = "Meu histórico de conversas",
            hasNotification = true
        )
        HorizontalDivider()
        MenuItem(
            iconResId = R.drawable.notificacao,
            title = "Notificações",
            subtitle = "Central de notificações",
            hasNotification = true
        )
        HorizontalDivider()
        MenuItem(
            iconResId = R.drawable.diamante,
            title = "Clube Bellus",
            subtitle = "Meus benefícios exclusivos"
        )
        HorizontalDivider()
        MenuItem(
            iconResId = R.drawable.contorno_em_forma_de_coracao,
            title = "Favoritos",
            subtitle = "Meus locais favoritos"
        )
        HorizontalDivider()
        MenuItem(
            iconResId = R.drawable.arquivo,
            title = "Dados da conta",
            subtitle = "Minhas informações da conta"
        )
        HorizontalDivider()
        MenuItem(
            iconResId = R.drawable.endereco,
            title = "Login de parceiro Bellus",
            subtitle = "Faça aqui o login do seu perfil de estabelecimento"
        )
        HorizontalDivider()
        MenuItem(
            iconResId = R.drawable.ajuda,
            title = "Ajuda",
            subtitle = "Central de ajuda"
        )
        HorizontalDivider()
        MenuItem(
            iconResId = R.drawable.configuracoes,
            title = "Configurações",
            subtitle = "Central de configurações"
        )
    }
}
