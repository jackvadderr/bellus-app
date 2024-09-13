package br.sapiens.bellus_app.data.repository.model

import br.sapiens.bellus_app.data.datasource.entity.CadastroDTO
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.presentation.ui.model.CategoryModel
import br.sapiens.bellus_app.presentation.ui.model.NewItemModel
import br.sapiens.bellus_app.presentation.ui.model.Offer

/**
 * Função de extensão para a classe UserDTO para mapeá-la para um modelo de User.
 *
 * @return Modelo de User com as mesmas propriedades que o UserDTO.
 */
//fun UserDTO.mapModel(): User {
//    return User(
//        username = username,
//        name = name,
//        phone = phone,
//        mail = email,
//        address = address,
//        gender = gender
//    )
//}

fun CadastroDTO.mapModel(): Cadastro {
    return Cadastro(
        email = email,
    )
}

fun EstabelecimentoDTO.toCategoryModel(): CategoryModel {
    return CategoryModel(
        name = this.nome,
        imageResource = this.imagem.firstOrNull()
            ?: "https://img.freepik.com/fotos-premium/fundo-branco-com-um-quadrado-branco-e-a-palavra-branco-nele_868698-119.jpg?w=1380"
    )
}

fun EstabelecimentoDTO.toNewItemModel(): NewItemModel {
    return NewItemModel(
        name = this.nome,
        location = this.endereco,
        imageResource = this.imagem.firstOrNull()
            ?: "https://img.freepik.com/fotos-premium/fundo-branco-com-um-quadrado-branco-e-a-palavra-branco-nele_868698-119.jpg?w=1380",
        rating = 4.5
    )
}

fun EstabelecimentoDTO.toOffer(): Offer {
    return Offer(
        title = this.nome,
        address = this.endereco,
        imageResource = this.imagem.firstOrNull()
            ?: "https://img.freepik.com/fotos-premium/fundo-branco-com-um-quadrado-branco-e-a-palavra-branco-nele_868698-119.jpg?w=1380"
    )
}
