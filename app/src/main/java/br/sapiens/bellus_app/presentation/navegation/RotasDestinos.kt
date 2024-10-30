package br.sapiens.bellus_app.presentation.navegation

sealed class RotasDestinos(val rota: String) {
    data object Splash : RotasDestinos("splash")
    data object LoginSocial : RotasDestinos("login-social")
    data object LoginCredencial : RotasDestinos("login-credencial")
    data object Cadastro : RotasDestinos("cadastro")
    data object Home : RotasDestinos("home")
    data object Perfil : RotasDestinos("perfil")
    data object Pesquisar : RotasDestinos("pesquisar")
    data object DetalhesEstabelecimento :
        RotasDestinos("details")

    data object EscolherProfissionalAgendamento : RotasDestinos("agendamentos")
    data object CriarAgendamento : RotasDestinos("criar-agendamentos") // TODO: Mudar nome
    data object GerenciarAgendamentos : RotasDestinos("gerenciar-agendamentos")

    data object CategoriaSelecionada : RotasDestinos("selecionar-categoria")

    data object ParceiroHome : RotasDestinos("parceiro")
    data object ParceiroCadastro : RotasDestinos("parceiro-cadastro")
    data object ParceiroProfile : RotasDestinos("parceiro-profile")
    data object ParceiroAgendamentos : RotasDestinos("parceiro-agendamentos")
    data object ParceiroListarAgendamentos : RotasDestinos("modificar-estabelecimento")
    data object ParceiroSelectedAppointmentManager :
        RotasDestinos("parceiro-selected-appointment-manager")

    data object ParceiroManagerEstablishment : RotasDestinos("parceiro-manager-establishment")
    data object ParceiroManagerService : RotasDestinos("parceiro-manager-service")
    data object ParceiroManagerProfissionais : RotasDestinos("parceiro-manager-profissionais")
    data object ParceiroManagerPortfolio : RotasDestinos("parceiro-manager-portfolio")
    data object ParceiroManagerAbout : RotasDestinos("parceiro-manager-about")
}