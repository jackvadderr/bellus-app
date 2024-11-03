package br.sapiens.bellus_app.presentation.telas.professionalSide

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import br.sapiens.bellus_app.R
import br.sapiens.bellus_app.presentation.ui.component.profile.MenuItem
import br.sapiens.bellus_app.presentation.viewmodels.parceiroSide.ParceiroProfileViewModel


@Composable
fun PerfilParceiro(
    viewModel: ParceiroProfileViewModel,
    navigateToSplash: () -> Unit,
) {
//    val userStore = viewModel.userStore
//    val coroutine = viewModel.coroutine
    Column {
//        ProfileHeader(name = "João Marcos") TODO: Pegar do DataStore
        MenuItem(
            iconResId = R.drawable.bubble_chat,
            title = "Dashboard",
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
        MenuItem(
            iconResId = R.drawable.ic_baseline_person_24,
            title = "Sair do modo profissional",
            subtitle = "Sair do modo profissional",
            onClick = {
//                val clientInfo = ClientInfo(
//                    id = "",
//                    name = "",
//                    email = "",
//                    phone = "",
//                    genero = "",
//                )
                viewModel.getLocalUser()
                navigateToSplash()
            }
        )
    }
}