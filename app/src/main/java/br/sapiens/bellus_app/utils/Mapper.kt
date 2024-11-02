package br.sapiens.bellus_app.utils

import br.sapiens.bellus_app.data.datasource.entity.AppointmentDTO
import br.sapiens.bellus_app.data.datasource.entity.CadastroDTO
import br.sapiens.bellus_app.data.datasource.entity.CategoryNameDTO
import br.sapiens.bellus_app.data.datasource.entity.DeleteDTO
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoSummaryDTO
import br.sapiens.bellus_app.data.datasource.entity.GetCategoryDTO
import br.sapiens.bellus_app.data.datasource.entity.ProfessionalDTO
import br.sapiens.bellus_app.data.datasource.entity.ReviewsDTO
import br.sapiens.bellus_app.data.datasource.entity.SearchDTO
import br.sapiens.bellus_app.data.datasource.entity.ServiceDTO
import br.sapiens.bellus_app.data.repository.model.Cadastro
import br.sapiens.bellus_app.data.repository.model.GeneroEnum
import br.sapiens.bellus_app.dominio.sdk.network.schemas.CadastroSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.DeleteSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PostServiceSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutServiceSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseAppointmentSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseCadastroSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseCategoryNameSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseCategorySchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseEstablishmentSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseProfessionalSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseSearchSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.ResponseServiceSchema
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
        description = this.description,
        cnjp = this.cnpj,
        profissionais_filiados = this.profissionaisFiliados,
        profisisonal_dono = this.profissionalDono,

        )
}

fun ResponseProfessionalSchema.toProfessionalDTO(): ProfessionalDTO {
    return ProfessionalDTO(
        id = this.id,
        userId = this.user_id,
        linkedEstablishmentId = this.linked_establishment_id,
        name = this.name,
        profession = this.profession,
        cpf = this.cpf,
    )
}

fun ResponseEstablishmentSchema.toEstablishmentDTO(): EstabelecimentoDTO {
    return EstabelecimentoDTO(
        id = this.id,
        nome = this.nome,
        cnpj = this.cnpj,
        average_rating = this.average_rating,
        endereco = this.endereco,
        telefone = this.telefone,
        horario_funcionamento = this.horario_funcionamento,
        imagem = this.imagem,
        portfolio = this.portfolio,
        description = this.description,
        created_at = this.created_at,
        updated_at = this.updated_at,
        profissionaisFiliados = this.profissionais_filiados,
        profissionalDono = this.profissional_dono,
    )
}


fun ServiceDTO.toServiceDetails(): ServiceDetails {
    return ServiceDetails(
        id = this.id,
        name = this.name,
        duration = this.duration,
        preco = this.price,
        description = this.description,
        establishmentId = this.establishment_id,
    )
}

fun ResponseServiceSchema.toServiceDTO(): ServiceDTO {
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

fun ResponseAppointmentSchema.toGetAppointmentDTO(): AppointmentDTO {
    return AppointmentDTO(
        id = this.id,
        userId = this.user_id,
        scheduled_date = this.scheduled_date,
        establishmentId = this.establishment_id,
        serviceId = this.service_id,
        statusRequest = this.status_request,
        completionDate = this.completion_date,
    )
}

fun ResponseAppointmentSchema.toAppointmentDTO(): AppointmentDTO {
    return AppointmentDTO(
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
        rating = this.average_rating
    )
}

fun ResponseSearchSchema.toSearchDTO(): SearchDTO {
    return SearchDTO(
        establishmentId = this.establishment_id,
    )
}

fun CadastroDTO.toCadastroSchema(senha: String): CadastroSchema {
    return CadastroSchema(
        name = this.name.toString(),
        phone = this.phone.toString(),
        email = this.email.toString(),
        isProfessional = this.isProfessional ?: false,
        genero = this.genero ?: GeneroEnum.MASCULINO,
        idade = this.idade ?: 18,
        senha = senha,
    )
}

fun ResponseCadastroSchema.toCadastroDTO(): CadastroDTO {
    return CadastroDTO(
        name = this.name,
        phone = this.phone,
        genero = this.genero,
        email = this.email,
        isProfessional = this.isProfessional,
        idade = this.idade,
    )
}

fun ServiceDetails.toPostServiceSchema(): PostServiceSchema {
    return PostServiceSchema(
        name = this.name,
        description = this.description,
        price = this.preco,
        duration = this.duration,
        establishment_id = this.establishmentId,
    )
}

fun ServiceDetails.toPostServiceSchema(establishmentId: String): PostServiceSchema {
    return PostServiceSchema(
        name = this.name,
        description = this.description,
        price = this.preco,
        duration = this.duration,
        establishment_id = establishmentId,
    )
}

fun ServiceDetails.toPutServiceSchema(establishmentId: String): PutServiceSchema {
    return PutServiceSchema(
        name = this.name,
        description = this.description,
        price = this.preco,
        duration = this.duration,
        establishment_id = establishmentId,
    )
}

fun DeleteSchema.toDeleteDTO(): DeleteDTO {
    return DeleteDTO(
        message = this.message
    )
}

//val name: String,
//val description: String,
//val price: Float,
//val duration: Duration,
//val establishment_id: String,