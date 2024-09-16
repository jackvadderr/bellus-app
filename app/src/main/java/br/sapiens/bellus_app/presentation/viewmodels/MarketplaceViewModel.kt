package br.sapiens.bellus_app.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.dominio.model.MarketplaceAction
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.dominio.sdk.storage.CoilImageLoaderProvider
import br.sapiens.bellus_app.dominio.sdk.storage.FirebaseStorageProvider
import br.sapiens.bellus_app.dominio.usecase.GetAllEstablishmentsUseCase
import br.sapiens.bellus_app.presentation.ui.model.EstablishmentDetails
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toServiceItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val getAllEstablishmentsUseCase: GetAllEstablishmentsUseCase,
    private val coilImageLoaderProvider: CoilImageLoaderProvider,
    private val storageProvider: FirebaseStorageProvider,
    private val marketplaceStore: MarketplaceStore,
) : BaseViewModel<MarketplaceViewModel.ViewState, MarketplaceViewModel.ViewEvent>() {

    val mkt = marketplaceStore

    init {
        viewModelScope.launch {
            marketplaceStore.store.stateFlow.collect { newState ->
                val establishmentDetails = newState.marketplaceState.establishmentDetails
                setState {
                    ViewState.UserLoaded(establishmentDetails)
                }
            }
        }
    }

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
                getAllEstablishmentsUseCase.execute(null)) {
                is State.Success -> {
                    Log.d("MarketplaceViewModel", "User data: ${result.data}")
                    val data: List<EstabelecimentoDTO> = result.data

                    val establishmentDetails: List<EstablishmentDetails> =
                        data.map { it.toServiceItem() }

                    marketplaceStore.dispatch(
                        MarketplaceAction.UpdateEstablishmentDetails(
                            establishmentDetails
                        )
                    )
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

    fun getMarketplaceStore(): MarketplaceStore {
        return marketplaceStore
    }

    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data class UserLoaded(
            val establishmentDetails: List<EstablishmentDetails>
        ) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object LoadUser : ViewEvent()
    }
}