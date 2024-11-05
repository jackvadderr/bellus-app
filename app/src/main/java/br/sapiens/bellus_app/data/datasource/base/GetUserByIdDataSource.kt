package br.sapiens.bellus_app.data.datasource.base

import br.sapiens.bellus_app.data.datasource.entity.UserDTO
import br.sapiens.bellus_app.utils.State

fun interface GetUserByIdDataSource {

    suspend fun getById(id: String): State<UserDTO>
}