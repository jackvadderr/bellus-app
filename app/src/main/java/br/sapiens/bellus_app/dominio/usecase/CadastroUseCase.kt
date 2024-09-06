package br.sapiens.bellus_app.dominio.usecase

import android.util.Log
import br.sapiens.bellus_app.base.Inputs
import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.repository.base.CadastroRepository
import br.sapiens.bellus_app.data.repository.model.Cadastro
import br.sapiens.bellus_app.data.repository.model.GeneroEnum
import br.sapiens.bellus_app.utils.State
import javax.inject.Inject


class CadastroUseCase @Inject constructor(
    private val cadastroRepository: CadastroRepository,
) : UseCase<CadastroUseCase.Input, Cadastro>() {

    override suspend fun invoke(input: Input?): State<Cadastro> {
        return try {
            // Attempt to register the user directly without checking for an existing user ID
            Log.d("CadastroUseCase", "Chamando cadastroRepository.register")
            when (val response = cadastroRepository.register(
                name = input?.name,
                phone = input?.phone,
                email = input?.email,
                senha = input?.senha,
                genero = input?.genero,
            )) {
                is State.Success -> {
                    Log.d("CadastroUseCase", "Cadastro realizado com sucesso")
                    response
                }
                is State.Error -> {
                    Log.e("CadastroUseCase", "Erro ao realizar cadastro", response.exception)
                    response
                }
            }
        } catch (exception: Exception) {
            Log.e("CadastroUseCase", "Exceção ao realizar cadastro", exception)
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