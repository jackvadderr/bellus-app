package br.sapiens.bellus_app.data.repository.model

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
        mail = mail,
        address = address,
        gender = gender
    )
}

