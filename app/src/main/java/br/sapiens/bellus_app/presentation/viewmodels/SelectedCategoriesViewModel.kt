package br.sapiens.bellus_app.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.datasource.entity.GetCategoryDTO
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.dominio.usecase.categories.GetCategoriesByNumberUseCase
import br.sapiens.bellus_app.dominio.usecase.establishment.GetEstablishmentByIdUseCase
import br.sapiens.bellus_app.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectedCategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesByNumberUseCase,
    private val getEstablishmentByIdUseCase: GetEstablishmentByIdUseCase,
    private val marketplaceStore: MarketplaceStore,
    coroutineScope: CoroutineScope
) : BaseViewModel<SelectedCategoriesViewModel.ViewState, SelectedCategoriesViewModel.ViewEvent>() {

    val mkt = marketplaceStore

    //    val scope = coroutineScope
    private val TAG = "SelectedCategoriesVM"


    init {
        triggerEvent(ViewEvent.Loading)
    }

    override fun createInitialState(): ViewState {
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.Loading -> {
                Log.d(TAG, "Event: Loading")
                getCategories()
            }

            ViewEvent.LoadCategories -> {
                Log.d(TAG, "Event: LoadCategories")
                getEstablishments()
            }

            ViewEvent.LoadEstablishments -> {
                Log.d(TAG, "Event: LoadEstablishments")
            }
        }
    }

    fun getEstablishments() {
        viewModelScope.launch {
            val categoryNumber: Int? = marketplaceStore.getCurrentNumberCategory()
            Log.d(TAG, "getEstablishments: categoryNumber = $categoryNumber")
            if (categoryNumber != null) {
                when (val result: State<List<GetCategoryDTO>> =
                    getCategoriesUseCase.invoke(categoryNumber.toString())) {
                    is State.Success -> {
                        Log.d(TAG, "getEstablishments: Success fetching categories")
                        val establishments = mutableListOf<EstabelecimentoDTO>()
                        result.data.forEach { category ->
                            when (val establishmentResult: State<EstabelecimentoDTO> =
                                getEstablishmentByIdUseCase.invoke(category.establishmentId)) {
                                is State.Success -> {
                                    Log.d(
                                        TAG,
                                        "getEstablishments: Success fetching establishment with id ${category.id}"
                                    )
                                    establishments.add(establishmentResult.data)
                                }

                                is State.Error -> {
                                    Log.e(
                                        TAG,
                                        "getEstablishments: Error fetching establishment with id ${category.id}"
                                    )
                                }
                            }
                        }
                        setState {
                            ViewState.LoadEstablishments(establishments)
                        }
                    }

                    is State.Error -> {
                        Log.e(TAG, "getEstablishments: Error fetching categories")
                    }
                }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            val categoryNumber: Int? = marketplaceStore.getCurrentNumberCategory()
            Log.d(TAG, "getCategories: categoryNumber = $categoryNumber")
            if (categoryNumber != null) {
                when (val result: State<List<GetCategoryDTO>> =
                    getCategoriesUseCase.invoke(categoryNumber.toString())) {
                    is State.Success -> {
                        Log.d(TAG, "getCategories: Success fetching categories")
                        setState {
                            ViewState.LoadCategories(result.data)
                        }
                    }

                    is State.Error -> {
                        Log.e(TAG, "getCategories: Error fetching categories")
                    }
                }
            }
        }
    }


    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data class LoadCategories(val categories: List<GetCategoryDTO>) :
            ViewState()

        data class LoadEstablishments(val establishments: List<EstabelecimentoDTO>) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object Loading : ViewEvent()
        data object LoadCategories : ViewEvent()
        data object LoadEstablishments : ViewEvent()
    }


}
