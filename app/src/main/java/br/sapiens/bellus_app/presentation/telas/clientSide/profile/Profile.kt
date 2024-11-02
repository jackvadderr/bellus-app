package br.sapiens.bellus_app.presentation.telas.clientSide.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.presentation.ui.component.profile.MenuItem
import br.sapiens.bellus_app.presentation.viewmodels.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun Profile(
    viewModel: ProfileViewModel,
    navigateToSplash: () -> Unit,
    navigateToCadastroParceiro: () -> Unit

) {
    val viewState by viewModel.uiState.collectAsState()

    val userStore = viewModel.userStore
    val coroutine = viewModel.coroutine

    Column {
//        ProfileHeader(name = "João Marcos") TODO: Pegar do DataStore

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
            subtitle = "Faça aqui o login do seu perfil de estabelecimento",
            onClick = {
                coroutine.launch(Dispatchers.Main) {
                    viewModel.getProfessionalInfo(
                        onSuccess = {
                            navigateToSplash()
                        },
                        onError = {
                            navigateToCadastroParceiro()
                        }
                    )
                }
            },
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