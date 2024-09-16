package br.sapiens.bellus_app.utils

import br.sapiens.bellus_app.data.datasource.entity.CadastroDTO
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.data.repository.model.Cadastro
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseServices
import br.sapiens.bellus_app.presentation.ui.model.CategoryModel
import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetails
import br.sapiens.bellus_app.presentation.ui.model.NewItemModel
import br.sapiens.bellus_app.presentation.ui.model.Offer
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails

fun CadastroDTO.mapModel(): Cadastro {
    return Cadastro(
        email = email,
    )
}

fun EstabelecimentoDTO.toCategoryModel(): CategoryModel {
    return CategoryModel(
        name = this.nome,
        imageResource = this.imagem.firstOrNull()
            ?: "https://img.freepik.com/fotos-premium/fundo-branco-com-um-quadrado-branco-e-a-palavra-branco-nele_868698-119.jpg?w=1380",
    )
}

fun EstabelecimentoDTO.toNewItemModel(): NewItemModel {
    return NewItemModel(
        id = this.id,
        name = this.nome,
        location = this.endereco,
        imageResource = this.imagem.firstOrNull()
            ?: "https://img.freepik.com/fotos-premium/fundo-branco-com-um-quadrado-branco-e-a-palavra-branco-nele_868698-119.jpg?w=1380",
        rating = this.rating
    )
}

fun EstabelecimentoDTO.toOffer(): Offer {
    return Offer(
        id = this.id,
        title = this.nome,
        address = this.endereco,
        imageResource = this.imagem.firstOrNull()
            ?: "https://img.freepik.com/fotos-premium/fundo-branco-com-um-quadrado-branco-e-a-palavra-branco-nele_868698-119.jpg?w=1380",
        rating = this.rating,
    )
}

fun EstabelecimentoDTO.toServiceItem(): EstablishmentDetails {
    return EstablishmentDetails(
        id = this.id,
        name = this.nome,
        address = this.endereco,
        telefone = this.telefone,
        rating = this.rating,
        imageResource = this.imagem.firstOrNull()
            ?: "https://img.freepik.com/fotos-premium/fundo-branco-com-um-quadrado-branco-e-a-palavra-branco-nele_868698-119.jpg?w=1380",
        portfolio = this.portfolio.firstOrNull() ?: "",
        horario_funcionamento = this.horario_funcionamento,

        )
}

fun ServiceDTO.toServiceDetails(): ServiceDetails {
    return ServiceDetails(
        id = this.id,
        name = this.name,
        duration = this.duration,
        preco = this.price,
    )
}

fun ResponseServices.toServiceDTO(): ServiceDTO {
    return ServiceDTO(
        id = this.id,
        name = this.name,
        description = this.description,
        price = this.price,
        duration = this.duration,
        establishment_id = this.establishment_id,
    )
}