package br.sapiens.bellus_app.base

import br.sapiens.bellus_app.utils.State

abstract class UseCase<Input : Any?, Output : Any> {

    protected abstract suspend fun invoke(input: Input?): State<Output>

    suspend fun execute(input: Input?): State<Output> {
        return invoke(input)
    }
}

interface Inputs