package br.sapiens.bellus_app.base

import br.sapiens.bellus_app.utils.State

/**
 * Classe abstrata UseCase que representa um caso de uso na aplicação.
 * É uma classe base para todos os casos de uso na aplicação.
 * Cada caso de uso é uma operação ou ação que a aplicação pode realizar.
 *
 * @param Input o tipo dos parâmetros de entrada para o caso de uso.
 * @param Output o tipo do resultado/saída do caso de uso.
 */
abstract class UseCase<Input : Any?, Output : Any> {

    /**
     * Função abstrata que cada caso de uso precisa implementar.
     * É chamada quando o caso de uso é executado.
     *
     * @param input os parâmetros de entrada para o caso de uso.
     * @return o resultado do caso de uso envolto em um objeto State.
     */
    protected abstract suspend fun invoke(input: Input?): State<Output>

    /**
     * Função para executar o caso de uso.
     * Chama a função abstrata invoke que cada caso de uso precisa implementar.
     *
     * @param input os parâmetros de entrada para o caso de uso.
     * @return o resultado do caso de uso envolto em um objeto State.
     */
    suspend fun execute(input: Input?): State<Output> {
        return invoke(input)
    }
}

/**
 * Interface para as entradas de um caso de uso.
 * É uma interface marcadora e não possui nenhum método.
 */
interface Inputs