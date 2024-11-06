package br.sapiens.bellus_app.presentation.navegation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import br.sapiens.bellus_app.presentation.telas.barra_navegation.BottomNavigation
import br.sapiens.bellus_app.presentation.telas.clientSide.appointment.create_appointment.TelaCreateAppointment
import br.sapiens.bellus_app.presentation.telas.clientSide.appointment.create_appointment.TelaSelectionProfissional
import br.sapiens.bellus_app.presentation.telas.clientSide.appointment.list_appointment.TelaAppointmentManager
import br.sapiens.bellus_app.presentation.telas.clientSide.home.TelaMarketplace
import br.sapiens.bellus_app.presentation.telas.clientSide.marketplaceSelection.TelaSelectionMarketplace
import br.sapiens.bellus_app.presentation.telas.clientSide.pesquisa.TelaPesquisa
import br.sapiens.bellus_app.presentation.telas.clientSide.pesquisa.TelaSelectedCategories
import br.sapiens.bellus_app.presentation.telas.clientSide.profile.Profile
import br.sapiens.bellus_app.presentation.telas.login.TelaCadastro
import br.sapiens.bellus_app.presentation.telas.login.TelaLoginCredenciais
import br.sapiens.bellus_app.presentation.telas.login.TelaSocialLogin
import br.sapiens.bellus_app.presentation.telas.professionalSide.PerfilParceiro
import br.sapiens.bellus_app.presentation.telas.professionalSide.TelaAgendamentoParceiro
import br.sapiens.bellus_app.presentation.telas.professionalSide.TelaCadastroParceiro
import br.sapiens.bellus_app.presentation.telas.professionalSide.TelaSelectedAppointmentManagerParceiro
import br.sapiens.bellus_app.presentation.telas.professionalSide.TelaUpdateEstablishmentParceiro
import br.sapiens.bellus_app.presentation.telas.splash.TelaSplash
import br.sapiens.bellus_app.presentation.ui.component.CustomTopBar
import br.sapiens.bellus_app.presentation.viewmodels.TelaNavegationBarViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavGraph(userProfileStore: UserProfileStore) {
    val currentActiveProfile = userProfileStore.getActiveProfileType()
    val startDestination: String = RotasDestinos.Splash.rota

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rotaAtual = navBackStackEntry?.destination?.route
    val telaNavegationviewModel: TelaNavegationBarViewModel = viewModel()
    val selectedItem = telaNavegationviewModel.selectedItem.observeAsState()


    val deveExibirBarraNavegacao: (String?) -> Boolean = { rota ->
        rota != RotasDestinos.Splash.rota &&
                rota != RotasDestinos.LoginSocial.rota &&
                rota != RotasDestinos.LoginCredencial.rota &&
                rota != RotasDestinos.Cadastro.rota &&
                rota != RotasDestinos.DetalhesEstabelecimento.rota &&
                rota != RotasDestinos.EscolherProfissionalAgendamento.rota &&
                rota != RotasDestinos.CriarAgendamento.rota

    }

    val naoDeveExibitTopBar: (String?) -> Boolean = { rota ->
        rota != RotasDestinos.Splash.rota &&
                rota != RotasDestinos.LoginSocial.rota &&
                rota != RotasDestinos.LoginCredencial.rota &&
                rota != RotasDestinos.Cadastro.rota &&
                rota != RotasDestinos.DetalhesEstabelecimento.rota &&
                rota != RotasDestinos.EscolherProfissionalAgendamento.rota &&
                rota != RotasDestinos.CriarAgendamento.rota
    }

    val rotasSemPadding = listOf(
        RotasDestinos.Splash.rota,
        RotasDestinos.LoginCredencial.rota,
        RotasDestinos.LoginSocial.rota,
        RotasDestinos.Cadastro.rota,
        RotasDestinos.DetalhesEstabelecimento.rota,
        RotasDestinos.Perfil.rota,
        RotasDestinos.EscolherProfissionalAgendamento.rota,
        RotasDestinos.CriarAgendamento.rota
    )

    val modifier = if (rotaAtual in rotasSemPadding) {
        Modifier
    } else {
        Modifier.padding(top = 60.dp, bottom = 65.dp)
    }

    Scaffold(
        topBar = {
            if (naoDeveExibitTopBar(rotaAtual)) {
                CustomTopBar()
            }
        },
        bottomBar = {
            if (deveExibirBarraNavegacao(rotaAtual)) {
                BottomNavigation(navController, selectedItem.value, currentActiveProfile)
            }
        }
    )
    { _ ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier
        ) {
            /* ######################
             *  SPLASH ART AMIGO
             * ######################
             */
            composable(RotasDestinos.Splash.rota) {
                TelaSplash(
                    hiltViewModel(),
                    navigateToHome = {
                        navController.navigate(
                            route = RotasDestinos.Home.rota,
                        ) { popUpTo(RotasDestinos.Splash.rota) { inclusive = true } }
                    },
                    navigateToLogin = {
                        navController.navigate(
                            route = RotasDestinos.LoginSocial.rota,
                        ) { popUpTo(RotasDestinos.Splash.rota) { inclusive = true } }
                    },
//                    navigateToParceiroHome = {
//                        navController.navigate(
//                            route = RotasDestinos.ParceiroHome.rota,
//                        ) { popUpTo(RotasDestinos.Splash.rota) { inclusive = true } }
//                    },
                    navigateToParceiroProfile = {
                        navController.navigate(
                            route = RotasDestinos.ParceiroProfile.rota,
                        ) { popUpTo(RotasDestinos.Splash.rota) { inclusive = true } }
                    }
                )
            }
            /* ######################
             *  AREA GERAL DE LOGIN AMIGO
             * ######################
             */
            composable(RotasDestinos.LoginSocial.rota) {
                TelaSocialLogin(
                    hiltViewModel(),
                    navigateToLoginCredencial = {
                        navController.navigate(
                            route = RotasDestinos.LoginCredencial.rota,
                        ) { popUpTo(RotasDestinos.LoginCredencial.rota) { inclusive = true } }
                    },
                    navigateToHome = {
                        navController.navigate(
                            route = RotasDestinos.Home.rota,
                        ) { popUpTo(RotasDestinos.LoginSocial.rota) { inclusive = true } }
                    }
                )
            }
            composable(RotasDestinos.LoginCredencial.rota) {
                TelaLoginCredenciais(
                    hiltViewModel(),
                    navigateToRegister = {
                        navController.navigate(
                            route = RotasDestinos.Cadastro.rota,
                        ) { popUpTo(RotasDestinos.LoginCredencial.rota) { inclusive = true } }
                    },
                    navigateToSplash = {
                        navController.navigate(
                            route = RotasDestinos.Splash.rota,
                        ) { popUpTo(RotasDestinos.LoginCredencial.rota) { inclusive = true } }
                    },
                )
            }
            composable(RotasDestinos.Cadastro.rota) {
                TelaCadastro(
                    hiltViewModel(),
                    navigateToBack = {
                        navController.navigate(
                            route = RotasDestinos.LoginSocial.rota
                        ) { popUpTo(RotasDestinos.LoginSocial.rota) { inclusive = true } }
                    }
                )
            }
            /* ######################
             *  MARKETPLACE AMIGÃO
             * ######################
             */
            // Tela Home
            composable(RotasDestinos.Home.rota) {
                TelaMarketplace(
                    hiltViewModel(),
                    navigateToDetails = {
                        navController.navigate(RotasDestinos.DetalhesEstabelecimento.rota)
                    },
                )
            }
            /* ################################
            *   TELA DE DETALHES DOS ESTABELECIMENTOS
            * #################################
            */
            // Tela Home
            composable(RotasDestinos.DetalhesEstabelecimento.rota) {
                TelaSelectionMarketplace(
                    hiltViewModel(),
                    navigateToBack = {
                        navController.navigate(
                            route = RotasDestinos.Home.rota
                        )
                    },
                    navigateToCreateAppointment = {
                        navController.navigate(
                            route = RotasDestinos.CriarAgendamento.rota
                        )
                    }
                )
            }
            /* ######################
             *   TELA DE PESQUISAS
             * ######################
             */
            // Tela Pesquisa
            composable(RotasDestinos.Pesquisar.rota) {
                TelaPesquisa(
                    hiltViewModel(),
                    navigateToCategories = {
                        navController.navigate(
                            route = RotasDestinos.CategoriaSelecionada.rota
                        )
                    },
                    navigateToEstablishmentDetails = {
                        navController.navigate(
                            route = RotasDestinos.DetalhesEstabelecimento.rota
                        )
                    }
                )
            }

            /* ######################
             *   DETALHES DE CATEGORIAS
             * ######################
             */
            composable(RotasDestinos.CategoriaSelecionada.rota) {
                TelaSelectedCategories(
                    hiltViewModel(),
                    navigateToBack = {
                        navController.navigate(
                            route = RotasDestinos.Pesquisar.rota
                        )
                    },
                    navigateToEstablishmentDetails = {
                        navController.navigate(
                            route = RotasDestinos.DetalhesEstabelecimento.rota
                        )
                    }
                )
            }

            /* ######################
             *   TELA DE PERFIL
             * ######################
             */
            composable(RotasDestinos.Perfil.rota) {
                Profile(
                    viewModel = hiltViewModel(),
                    navigateToSplash = {
                        navController.navigate(
                            route = RotasDestinos.Splash.rota
                        )
                    },
                    navigateToCadastroParceiro = {
                        navController.navigate(
                            route = RotasDestinos.ParceiroCadastro.rota
                        )
                    }
                )
            }
            // Tela de Entrar como Parceiro
            composable(RotasDestinos.ParceiroProfile.rota) {
                PerfilParceiro(
                    viewModel = hiltViewModel(),
                    navigateToSplash = {
                        navController.navigate(
                            route = RotasDestinos.Splash.rota
                        )
                    }
                )
            }
            // Cadastrar como parceiro caso NÃO SEJA AINDA
            composable(RotasDestinos.ParceiroCadastro.rota) {
                TelaCadastroParceiro(
                    hiltViewModel(),
                    navigateToSplash = {
                        navController.navigate(
                            route = RotasDestinos.Splash.rota
                        )
                    }
                )
            }
            // Home do parceiro mostrando todas os estabelecimento associado
            composable(RotasDestinos.ParceiroManagerEstablishment.rota) {
                TelaUpdateEstablishmentParceiro(
                    hiltViewModel(),
                    navigateToTelaUpdateEstablishment = {
                        navController.navigate(
                            route = RotasDestinos.ParceiroManagerEstablishment.rota
                        )
                    }
                )
            }
            composable(RotasDestinos.ParceiroListarAgendamentos.rota) {
                TelaAgendamentoParceiro(
                    hiltViewModel(),
                    navigateToSelectedAppointmentParceiro = {
                        navController.navigate(
                            route = RotasDestinos.ParceiroSelectedAppointmentManager.rota
                        )
                    }
                )
            }
            // Tela de agendamentos para aceitar ou rejeitar ou terminar
            composable(RotasDestinos.ParceiroSelectedAppointmentManager.rota) {
                TelaSelectedAppointmentManagerParceiro(
                    hiltViewModel(),
                    navigateToBack = {
                        navController.navigate(
                            route = RotasDestinos.ParceiroListarAgendamentos.rota
                        )
                    }
                )
            }
            // A partir daqui são as telas para fazer o gerenciamento dos estabelecimentos
//            composable(RotasDestinos.ParceiroManagerEstablishment.rota) {
//                TelaUpdateEstablishmentParceiro(
//                    hiltViewModel(),
//                    navigateToUpdateService = {
//                        navController.navigate(
//                            route = RotasDestinos.ParceiroHome.rota
//                        )
//                    }
//                )
//            }


            /* ######################
             *   TELA DE AGENDAMENTOS
             * ######################
             */
            // Escolher profissional
            composable(RotasDestinos.EscolherProfissionalAgendamento.rota) {
                TelaSelectionProfissional(
                    viewModel = hiltViewModel(),
                    navigateToCreateAppointment = {
                        navController.navigate(
                            route = RotasDestinos.CriarAgendamento.rota,
                        )
                    },
                    navigateToBack = {
                        navController.navigate(
                            route = RotasDestinos.DetalhesEstabelecimento.rota
                        )
                    }
                )
            }
            // Criar agendamento
            composable(RotasDestinos.CriarAgendamento.rota) {
                TelaCreateAppointment(
                    viewModel = hiltViewModel(),
                    navigateToBack = {
                        navController.navigate(
                            route = RotasDestinos.EscolherProfissionalAgendamento.rota
                        )
                    },
                    navigateToManagerAppointments = {
                        navController.navigate(
                            route = RotasDestinos.GerenciarAgendamentos.rota,
                        )
                    },
                )
            }
            // Listar agendamentos
            composable(RotasDestinos.GerenciarAgendamentos.rota) {
                TelaAppointmentManager(viewModel = hiltViewModel())
            }
        }
    }
}