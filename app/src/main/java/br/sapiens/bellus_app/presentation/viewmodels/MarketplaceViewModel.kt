package br.sapiens.bellus_app.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.repository.model.toCategoryModel
import br.sapiens.bellus_app.data.repository.model.toNewItemModel
import br.sapiens.bellus_app.data.repository.model.toOffer
import br.sapiens.bellus_app.dominio.sdk.storage.CoilImageLoaderProvider
import br.sapiens.bellus_app.dominio.sdk.storage.FirebaseStorageProvider
import br.sapiens.bellus_app.dominio.usecase.GetAllEstablishmentsUseCase
import br.sapiens.bellus_app.presentation.ui.model.CategoryModel
import br.sapiens.bellus_app.presentation.ui.model.NewItemModel
import br.sapiens.bellus_app.presentation.ui.model.Offer
import br.sapiens.bellus_app.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val getAllEstablishmentsUseCase: GetAllEstablishmentsUseCase,
    private val coilImageLoaderProvider: CoilImageLoaderProvider,
    private val storageProvider: FirebaseStorageProvider
) : BaseViewModel<MarketplaceViewModel.ViewState, MarketplaceViewModel.ViewEvent>() {

    override fun createInitialState(): ViewState {
        Log.d("MarketplaceViewModel", "Creating initial state")
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        Log.d("MarketplaceViewModel", "Triggering event: $event")
        when (event) {
            is ViewEvent.LoadUser -> loadUser()
        }
    }

    private fun loadUser() {
        Log.d("MarketplaceViewModel", "Loading user")
        viewModelScope.launch {
            setState {
                Log.d("MarketplaceViewModel", "Setting state to Loading")
                ViewState.Loading
            }
            when (val result: State<List<EstabelecimentoDTO>> =
                getAllEstablishmentsUseCase.invoke(null)) {
                is State.Success -> {
                    Log.d("MarketplaceViewModel", "User data: ${result.data}")
                    val data: List<EstabelecimentoDTO> = result.data

                    val categoryModels: List<CategoryModel> = data.map { it.toCategoryModel() }
                    val newItemModels: List<NewItemModel> = data.map { it.toNewItemModel() }
                    val offerModels: List<Offer> = data.map { it.toOffer() }

                    setState {
                        Log.d("MarketplaceViewModel", "Setting state to UserLoaded")
                        ViewState.UserLoaded(
                            categories = categoryModels,
                            newItems = newItemModels,
                            offers = offerModels
                        )
                    }
                }

                is State.Error -> {
                    Log.e("MarketplaceViewModel", "Error loading user data: ${result.exception}")
                    setState {
                        Log.d("MarketplaceViewModel", "Setting state to Loading")
                        ViewState.Loading
                    }
                }
            }
        }
    }


    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data class UserLoaded(
            val categories: List<CategoryModel>,
            val newItems: List<NewItemModel>,
            val offers: List<Offer>
        ) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object LoadUser : ViewEvent()
    }
}