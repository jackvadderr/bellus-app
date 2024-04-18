package br.sapiens.bellus_app.dominio.usecase

import br.sapiens.bellus_app.base.UseCase
import br.sapiens.bellus_app.data.repository.base.CheckUserRepository
import br.sapiens.bellus_app.utils.State


class CheckUserUseCase(
    private val checkUserRepository: CheckUserRepository,
) : UseCase<Nothing, Nothing>() {
    override suspend fun invoke(input: Nothing?): State<Nothing> {
        TODO("Ainda não implementado")
    }
}