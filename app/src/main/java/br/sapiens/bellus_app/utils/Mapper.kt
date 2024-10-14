package br.sapiens.bellus_app.utils

import br.sapiens.bellus_app.data.datasource.entity.CadastroDTO
import br.sapiens.bellus_app.data.datasource.entity.CategoryNameDTO
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoSummaryDTO
import br.sapiens.bellus_app.data.datasource.entity.GetAppointmentDTO
import br.sapiens.bellus_app.data.datasource.entity.GetCategoryDTO
import br.sapiens.bellus_app.data.datasource.entity.ReviewsDTO
import br.sapiens.bellus_app.data.datasource.entity.SearchDTO
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.data.repository.model.Cadastro
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseAppointmentSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseCategoryNameSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseCategorySchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseSearchSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseService
import br.sapiens.bellus_app.presentation.ui.component.sections.Category
import br.sapiens.bellus_app.presentation.ui.model.AvailableEstablishment
import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetail
import br.sapiens.bellus_app.presentation.ui.model.ReviewsDetails
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails

fun CadastroDTO.mapModel(): Cadastro {
    return Cadastro(
        email = email,
    )
}

fun EstabelecimentoSummaryDTO.toAvailableEstablishment(average_rating: Float): AvailableEstablishment {
    return AvailableEstablishment(
        id = this.id,
        name = this.nome,
        address = this.endereco,
        rating = average_rating,
        imageResource = this.imagem.firstOrNull()
            ?: "https://img.freepik.com/fotos-premium/fundo-branco-com-um-quadrado-branco-e-a-palavra-branco-nele_868698-119.jpg?w=1380",
    )
}

fun EstabelecimentoDTO.toEstablishmentDetail(
    total: Int,
    average_rating: Float
): EstablishmentDetail {
    return EstablishmentDetail(
        id = this.id,
        name = this.nome,
        address = this.endereco,
        telefone = this.telefone,
        rating = average_rating,
        totalReviews = total,
        imageResource = this.imagem,
        portfolio = this.portfolio,
        horario_funcionamento = this.horario_funcionamento,
        reviews_id = this.reviews_id,
        description = this.description,
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

fun ResponseService.toServiceDTO(): ServiceDTO {
    return ServiceDTO(
        id = this.id,
        name = this.name,
        description = this.description,
        price = this.price,
        duration = this.duration,
        establishment_id = this.establishment_id,
    )
}

fun ReviewsDTO.toReviewsDetails(): ReviewsDetails {
    return ReviewsDetails(
        id = this.id,
        nome = this.name,
        comment = this.comment,
        rating = this.rating,
        update_at = this.updated_at
    )
}

fun ResponseAppointmentSchema.toGetAppointmentDTO(): GetAppointmentDTO {
    return GetAppointmentDTO(
        id = this.id,
        userId = this.user_id,
        scheduled_date = this.scheduled_date,
        establishmentId = this.establishment_id,
        serviceId = this.service_id,
        statusRequest = this.status_request,
        completionDate = this.completion_date,
    )
}

fun ResponseCategorySchema.toCategoryDTO(): GetCategoryDTO {
    return GetCategoryDTO(
        id = this.id,
        nameCategory = this.category,
        establishmentId = this.establishment_id,
    )
}

fun CategoryNameDTO.toCategory(): Category {
    return Category(
        imageRes = this.imagem,
        title = this.name,
        number = this.number,
    )
}

fun ResponseCategoryNameSchema.toCategoryNameDTO(): CategoryNameDTO {
    return CategoryNameDTO(
        name = this.name,
        number = this.number,
        imagem = this.url,
    )
}

fun EstabelecimentoDTO.toAvailableEstablishment(): AvailableEstablishment {
    return AvailableEstablishment(
        id = this.id,
        name = this.nome,
        address = this.endereco,
        imageResource = this.imagem.firstOrNull().toString(),
        rating = this.rating
    )
}

fun ResponseSearchSchema.toSearchDTO(): SearchDTO {
    return SearchDTO(
        establishmentId = this.establishment_id,
    )
}