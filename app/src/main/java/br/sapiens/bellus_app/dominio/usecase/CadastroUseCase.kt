package br.sapiens.bellus_app.dominio.usecase

import br.sapiens.bellus_app.base.Inputs
import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.repository.base.CadastroRepository
import br.sapiens.bellus_app.data.repository.model.Cadastro
import br.sapiens.bellus_app.data.repository.model.GeneroEnum
import br.sapiens.bellus_app.dominio.sdk.AuthService
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.UserNotFoundException
import javax.inject.Inject


class CadastroUseCase @Inject constructor(
    private val authService: AuthService,
    private val cadastroRepository: CadastroRepository,
) : UseCase<CadastroUseCase.Input, Cadastro>() {

    override suspend fun invoke(input: Input?): State<Cadastro> {
        return try {
            authService.userId?.let {
                when (val response = cadastroRepository.register(
                    name = input?.name,
                    phone = input?.phone,
                    email = input?.email,
                    senha = input?.senha,
                    genero = input?.genero,
                )) {
                    is State.Success -> response
                    is State.Error -> response
                }
            } ?: run {
                State.Error(UserNotFoundException("User not found!"))
            }
        } catch (exception: Exception) {
            State.Error(exception)
        }
    }

    data class Input(
        val uuid: String? = null,
        val name: String? = null,
        val phone: String? = null,
        val email: String? = null,
        val genero: GeneroEnum? = null,
        val senha: String? = null,
    ) : Inputs
}