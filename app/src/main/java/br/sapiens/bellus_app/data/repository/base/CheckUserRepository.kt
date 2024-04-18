package br.sapiens.bellus_app.data.repository.base

/**
 * Esta é uma interface funcional que representa um repositório para verificar o usuário.
 * Possui um único método abstrato `checkUser`.
 * O método é uma função suspensa, o que significa que foi projetado para ser usado com corotinas e pode ser suspenso sem bloquear uma thread.
 */
fun interface CheckUserRepository {
    /**
     * Esta é uma função suspensa que verifica o usuário.
     * A implementação desta função deve conter a lógica para verificar o usuário.
     * Como uma função suspensa, ela pode ser pausada e retomada em momentos posteriores sem bloquear uma thread.
     */
    suspend fun checkUser()
}