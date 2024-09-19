package br.sapiens.bellus_app.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.dominio.model.MarketplaceEvent
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.dominio.usecase.GetEstablishmentsSummariesUseCase
import br.sapiens.bellus_app.presentation.ui.model.AvailableEstablishment
import br.sapiens.bellus_app.utils.State
import br.sapiens.bellus_app.utils.toAvailableEstablishment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val useCase: GetEstablishmentsSummariesUseCase,
    private val marketplaceStore: MarketplaceStore,
) : BaseViewModel<MarketplaceViewModel.ViewState, MarketplaceViewModel.ViewEvent>() {

    val mkt = marketplaceStore

    init {
        triggerEvent(ViewEvent.LoadUser)
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
        viewModelScope.launch {
            // Garante que o estado Loading seja emitido antes de qualquer outra operação
            setState { ViewState.Loading }

            val establishmentDetails =
                marketplaceStore.store.stateFlow.value.marketplaceState.establishmentSummaries
            if (establishmentDetails.isNotEmpty()) {
                // Caso os dados já estejam disponíveis no store, atualizar o estado diretamente
                setState { ViewState.UserLoaded(establishmentDetails) }
            } else {
                when (val result = useCase.execute(null)) {
                    is State.Success -> {
                        val establishmentAvailables =
                            result.data.map { it.toAvailableEstablishment() }
                        marketplaceStore.dispatch(
                            MarketplaceEvent.SuccessGetEstablishmentSummary(establishmentAvailables)
                        )
                        setState { ViewState.UserLoaded(establishmentAvailables) }
                    }

                    is State.Error -> {
                        Log.e(
                            "MarketplaceViewModel",
                            "Error loading user data: ${result.exception}"
                        )
                        setState { ViewState.Loading }
                    }
                }
            }
        }
    }

    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data class UserLoaded(
            val availableEstablishments: List<AvailableEstablishment>
        ) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object LoadUser : ViewEvent()
    }
}