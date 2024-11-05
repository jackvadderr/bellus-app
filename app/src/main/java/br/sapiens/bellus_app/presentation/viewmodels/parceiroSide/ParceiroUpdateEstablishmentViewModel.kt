package br.sapiens.bellus_app.presentation.viewmodels.parceiroSide

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.dominio.redux.stores.PermissionStore
import br.sapiens.bellus_app.dominio.redux.stores.UserProfileStore
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutEstablishmentSchema
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutEstablishmentSchemaEncapsulation
import br.sapiens.bellus_app.dominio.sdk.network.schemas.PutServiceSchemaEncapsulation
import br.sapiens.bellus_app.dominio.sdk.storage.FirebaseStorageProvider
import br.sapiens.bellus_app.dominio.usecase.establishment.GetEstablishmentByIdUseCase
import br.sapiens.bellus_app.dominio.usecase.establishment.PutEstablishmentUseCase
import br.sapiens.bellus_app.dominio.usecase.review.GetReviewsByEstablishmentIdUseCase
import br.sapiens.bellus_app.dominio.usecase.review.GetReviewsSummaryByEstablishmentIdUseCase
import br.sapiens.bellus_app.dominio.usecase.service.DeleteServiceUseCase
import br.sapiens.bellus_app.dominio.usecase.service.GetServicesByEstablishmentUseCase
import br.sapiens.bellus_app.dominio.usecase.service.PostServiceUseCase
import br.sapiens.bellus_app.dominio.usecase.service.PutServiceUseCase
import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetail
import br.sapiens.bellus_app.presentation.ui.model.ServiceDetails
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toEstablishmentDetail
import br.sapiens.bellus_app.utils.toPostServiceSchema
import br.sapiens.bellus_app.utils.toPutEstablishmentSchema
import br.sapiens.bellus_app.utils.toPutServiceSchema
import br.sapiens.bellus_app.utils.toServiceDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParceiroUpdateEstablishmentViewModel @Inject constructor(
    private val marketplaceStore: MarketplaceStore,
    private val userStore: UserProfileStore,
    private val getServicesUseCase: GetServicesByEstablishmentUseCase,
    private val getEstablishmentUseCase: GetEstablishmentByIdUseCase,
    private val putEstablishmentUseCase: PutEstablishmentUseCase,
    private val updateEstablishmentUseCase: PutEstablishmentUseCase,
    private val postServiceUseCase: PostServiceUseCase,
    private val putServiceUseCase: PutServiceUseCase,
    private val deleteServiceUseCase: DeleteServiceUseCase,
    private val reviewsUseCase: GetReviewsByEstablishmentIdUseCase,
    private val reviewsSummaryUseCase: GetReviewsSummaryByEstablishmentIdUseCase,
    private val firebaseStorage: FirebaseStorageProvider,
    val permissionStore: PermissionStore,
    coroutineScope: CoroutineScope,
    context: Context,
) : BaseViewModel<ParceiroUpdateEstablishmentViewModel.ViewState, ParceiroUpdateEstablishmentViewModel.ViewEvent>() {

    val mkt = marketplaceStore
    val scope = coroutineScope
    val firebaseStorageProvider = firebaseStorage
    val myContext = context

    var supremeEstablishmentId: String? = null
    var supremeListServices: List<ServiceDetails> = emptyList()

    init {
        Log.d("ParceiroUpdateEstablishmentViewModel", "ViewModel initialized")
        triggerEvent(ViewEvent.Loading)
    }

    override fun createInitialState(): ViewState {
        Log.d("ParceiroUpdateEstablishmentViewModel", "Creating initial state")
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.Loading -> getEstablishment()
            is ViewEvent.LoadServices -> supremeEstablishmentId?.let { id ->
                viewModelScope.launch { loadServices(id) }
            }

            is ViewEvent.UpdateEstablishment -> putEstablishment(event.updatedEstablishment)
            is ViewEvent.CreateService -> {
                createService(event.service)
            }

            is ViewEvent.UpdateService -> {
                updateService(event.service)
            }

            is ViewEvent.DeleteService -> {
                deleteService(event.service)
            }

            is ViewEvent.OpenGallery -> {
                Log.d("ParceiroUpdateEstablishmentViewModel", "Triggering open gallery")
                checkAndRequestGalleryPermission(
                    event.context,
                    event.permission,
                    event.launcher
                )
//                context: Context,
//                val permission: String,
//                val launcher:
            }

            is ViewEvent.UpdateAboutEstablihsment -> {
                updateEstablishment(event.updatedEstablishment)
            }
        }
    }

    fun checkAndRequestGalleryPermission(
        context: Context,
        permission: String,
        launcher: ManagedActivityResultLauncher<String, Boolean>
    ) {
        val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)
        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            // Open gallery because permission is already granted
            launcher.launch(permission)
            launcher.launch("image/*")
        } else {
            // Request a permission
            launcher.launch(permission)
        }
    }

    fun checkAndRequestCameraPermission(
        context: Context,
        permission: String,
        launcher: ManagedActivityResultLauncher<String, Boolean>
    ) {
        val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)
        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            // Open camera because permission is already granted
        } else {
            // Request a permission
            launcher.launch(permission)
        }
    }

    private fun deleteService(service: ServiceDetails) {
        viewModelScope.launch {
            when (deleteServiceUseCase.invoke(service.id)) {
                is State.Success -> {
                    Log.d("ParceiroUpdateEstablishmentViewModel", "Service deleted successfully")
                    triggerEvent(ViewEvent.LoadServices)
                }

                is State.Error -> {
                    Log.e("ParceiroUpdateEstablishmentViewModel", "Failed to delete service")
                }
            }
        }
    }

    private fun updateService(service: ServiceDetails) {
        viewModelScope.launch {
            val serviceSchema =
                service.toPutServiceSchema(establishmentId = supremeEstablishmentId!!)
            val encapsulation: PutServiceSchemaEncapsulation = PutServiceSchemaEncapsulation(
                id = service.id,
                schema = serviceSchema
            )
            Log.d("ParceiroUpdateEstablishmentViewModel", "serviceSchema: $serviceSchema")
            when (putServiceUseCase.invoke(encapsulation)) {
                is State.Success -> {
                    Log.d("ParceiroUpdateEstablishmentViewModel", "Service updated successfully")
                    triggerEvent(ViewEvent.LoadServices)
                }

                is State.Error -> {
                    Log.e("ParceiroUpdateEstablishmentViewModel", "Failed to update service")
                }
            }
        }
    }

    private fun createService(service: ServiceDetails) {
        viewModelScope.launch {
            val serviceSchema =
                service.toPostServiceSchema(establishmentId = supremeEstablishmentId!!)
            Log.d("ParceiroUpdateEstablishmentViewModel", "serviceSchema: $serviceSchema")
            when (postServiceUseCase.invoke(serviceSchema)) {
                is State.Success -> {
                    Log.d("ParceiroUpdateEstablishmentViewModel", "Service created successfully")
                    triggerEvent(ViewEvent.LoadServices)
                }

                is State.Error -> {
                    Log.e("ParceiroUpdateEstablishmentViewModel", "Failed to create service")
                }
            }
        }
    }

    private fun getEstablishment() {
        viewModelScope.launch {
            val establishmentId = fetchEstablishmentId() ?: return@launch
            loadEstablishmentData(establishmentId)
        }
    }

    private fun updateEstablishment(updated: EstablishmentDetail) {
        viewModelScope.launch {
            val schema = updated.toPutEstablishmentSchema()
            val encapulation = PutEstablishmentSchemaEncapsulation(
                id = supremeEstablishmentId.toString(),
                schema = schema
            )
            when (putEstablishmentUseCase.invoke(encapulation)) {
                is State.Success -> {}
                is State.Error -> {}
            }
        }
    }

    private fun fetchEstablishmentId(): String? {
        userStore.getEstablishmentId()?.let { id ->
            if (id.isNotEmpty()) {
                supremeEstablishmentId = id
                Log.d(
                    "ParceiroUpdateEstablishmentViewModel",
                    "Loaded establishment ID from userStore: $id"
                )
                setState { ViewState.LoadedCurrentEstablishmentId(id) }
                return id
            }
        }
        // Fallback para marketplaceStore
        marketplaceStore.store.stateFlow.value.marketplaceState.currentEstablishmentItemId?.let { id ->
            if (id.isNotEmpty()) {
                supremeEstablishmentId = id
                Log.d(
                    "ParceiroUpdateEstablishmentViewModel",
                    "Loaded establishment ID from marketplaceStore: $id"
                )
                setState { ViewState.LoadedCurrentEstablishmentId(id) }
                return id
            }
        }
        return null
    }


    private suspend fun loadEstablishmentData(establishmentId: String) {
        if (establishmentId.isNotEmpty()) {
            when (val result = getEstablishmentUseCase.execute(establishmentId)) {
                is State.Success -> {
                    setState {
                        ViewState.LoadedCurrentEstablishment(
                            currentEstablishment = result.data.toEstablishmentDetail(
                                0,
                                result.data.average_rating
                            )
                        )
                    }
                    triggerEvent(ViewEvent.LoadServices)
                }

                is State.Error -> {
                    Log.e("ParceiroUpdateEstablishmentViewModel", "Error loading establishment")
                }
            }
        }
    }

    private suspend fun loadServices(establishmentId: String) {
        when (val result =
            getServicesUseCase.execute(GetServicesByEstablishmentUseCase.Input(establishmentId))) {
            is State.Success -> {
                val serviceDetails = result.data.map { it.toServiceDetails() }
                setState {
                    val currentState = this
                    if (currentState is ViewState.LoadedCurrentEstablishment) {
                        currentState.copy(services = serviceDetails)
                    } else currentState
                }
            }

            is State.Error -> {
                Log.e("ParceiroUpdateEstablishmentViewModel", "Error loading services")
            }
        }
    }


    private fun putEstablishment(updatedEstablishment: EstablishmentDetail) {
        viewModelScope.launch {
            val encap = PutEstablishmentSchemaEncapsulation(
                id = updatedEstablishment.id,
                PutEstablishmentSchema(
                    cnpj = updatedEstablishment.cnjp,
                    nome = updatedEstablishment.name,
                    endereco = updatedEstablishment.address,
                    telefone = updatedEstablishment.telefone,
                    horario_funcionamento = updatedEstablishment.horario_funcionamento,
                    imagem = updatedEstablishment.imageResource,
                    portfolio = updatedEstablishment.portfolio,
                    description = updatedEstablishment.description,
                    profissionais_filiados = emptyList(),
                    profissional_dono = updatedEstablishment.profisisonal_dono,
                )

            )
            when (val result = updateEstablishmentUseCase.invoke(encap)) {
                is State.Success -> {
                    Log.d(
                        "ParceiroUpdateEstablishmentViewModel",
                        "Establishment updated successfully"
                    )
                    setState { ViewState.EstablishmentUpdated }
                }

                is State.Error -> {
                    Log.e(
                        "ParceiroUpdateEstablishmentViewModel",
                        "Failed to update establishment",
                        result.exception
                    )
//                    setState { ViewState.ErrorUpdatingEstablishment }
                }
            }
        }
    }


    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data class LoadedCurrentEstablishmentId(val id: String) : ViewState()
        data class LoadedCurrentEstablishment(
            val currentEstablishment: EstablishmentDetail,
            val services: List<ServiceDetails> = emptyList()
        ) :
            ViewState()

        data object EstablishmentUpdated : ViewState()
//        data object ErrorUpdatingEstablishment : ViewState()

//        data class LoadedServices(val services: List<ServiceDetails>) : ViewState()

//        data class UserLoaded( // Entendi, ele carregar a lista dos serviços e reviews. Que bosta :(
//            val establishmentDetails: EstablishmentDetail,
//            val serviceDetails: List<ServiceDetails>,
//            val reviewsDetails: List<ReviewsDetails>
//        ) : ViewState()

//        data object ErrorServicesNotFound : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object Loading : ViewEvent()
        data object LoadServices : ViewEvent()
        data class CreateService(val service: ServiceDetails) : ViewEvent()
        data class UpdateService(val service: ServiceDetails) : ViewEvent()
        data class DeleteService(val service: ServiceDetails) : ViewEvent()
        data class UpdateEstablishment(val updatedEstablishment: EstablishmentDetail) : ViewEvent()
        data class UpdateAboutEstablihsment(val updatedEstablishment: EstablishmentDetail) :
            ViewEvent()

        data class OpenGallery(
            val context: Context,
            val permission: String,
            val launcher: ManagedActivityResultLauncher<String, Boolean>
        ) : ViewEvent()


    }
}