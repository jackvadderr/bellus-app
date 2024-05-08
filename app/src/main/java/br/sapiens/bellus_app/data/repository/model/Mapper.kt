package br.sapiens.bellus_app.data.repository.model

import br.sapiens.bellus_app.data.datasource.entity.CadastroDTO
import br.sapiens.bellus_app.data.datasource.entity.UserDTO

/**
 * Função de extensão para a classe UserDTO para mapeá-la para um modelo de User.
 *
 * @return Modelo de User com as mesmas propriedades que o UserDTO.
 */
fun UserDTO.mapModel(): User {
    return User(
        username = username,
        name = name,
        phone = phone,
        mail = email,
        address = address,
        gender = gender
    )
}

fun CadastroDTO.mapModel(): Cadastro {
    return Cadastro(
//        username = username,
//        name = name,
//        phone = phone,
        email = email,
//        gender = gender,
    )
}

