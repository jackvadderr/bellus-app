package br.sapiens.bellus_app.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import br.sapiens.bellus_app.base.BaseViewModel
import br.sapiens.bellus_app.base.IViewEvent
import br.sapiens.bellus_app.base.IViewState
import br.sapiens.bellus_app.data.datasource.entity.CategoryNameDTO
import br.sapiens.bellus_app.data.datasource.entity.EstabelecimentoDTO
import br.sapiens.bellus_app.data.datasource.entity.GetCategoryDTO
import br.sapiens.bellus_app.data.datasource.entity.SearchDTO
import br.sapiens.bellus_app.dominio.redux.stores.MarketplaceStore
import br.sapiens.bellus_app.dominio.usecase.categories.GetCategoriesByNumberUseCase
import br.sapiens.bellus_app.dominio.usecase.categories.GetCategoriesListNameUseCase
import br.sapiens.bellus_app.dominio.usecase.establishment.GetEstablishmentByIdUseCase
import br.sapiens.bellus_app.dominio.usecase.search.GetSearchUseCase
import br.sapiens.bellus_app.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PesquisaViewModel @Inject constructor(
    private val categoriesUseCase: GetCategoriesByNumberUseCase,
    private val establishmentByIdUseCase: GetEstablishmentByIdUseCase,
    private val searchUseCase: GetSearchUseCase,
    private val listCategoriesUseCase: GetCategoriesListNameUseCase,
    marketplaceStore: MarketplaceStore,
    coroutineScope: CoroutineScope
) : BaseViewModel<PesquisaViewModel.ViewState, PesquisaViewModel.ViewEvent>() {

    val mkt = marketplaceStore
    val scope = coroutineScope

    init {
        triggerEvent(ViewEvent.Loading)
    }

    override fun createInitialState(): ViewState {
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        when (event) {
            is ViewEvent.Loading -> {
                loadCategoriesList()
            }

            ViewEvent.LoadCategories -> {

            }

            is ViewEvent.Search -> {
                
            }
        }
    }

    private fun loadCategoriesList() {
        viewModelScope.launch {
            when (val result: State<List<CategoryNameDTO>> = listCategoriesUseCase.invoke(null)) {
                is State.Success -> {
                    setState {
                        ViewState.LoadCategoriesList(result.data)
                    }
                }

                is State.Error -> {}
            }
        }
    }

    suspend fun getEstablishment(query: String): List<EstabelecimentoDTO> {
        return withContext(Dispatchers.IO) {
            val establishmentsId = getSearch(query)
            val establishments = mutableListOf<EstabelecimentoDTO>()
            establishmentsId.mapNotNull { searchDTO ->
                val id = searchDTO.establishmentId
                when (val result: State<EstabelecimentoDTO> = establishmentByIdUseCase.invoke(id)) {
                    is State.Success -> {
                        establishments.add(result.data)
                    }

                    is State.Error -> {
                        null
                    }
                }
            }
            setState {
                ViewState.SearchResults(establishments)
            }
            establishments
        }
    }

    suspend fun getSearch(query: String): List<SearchDTO> {
        return withContext(Dispatchers.IO) {
            var searchResult = mutableListOf<SearchDTO>()
            when (val result: State<List<SearchDTO>> = searchUseCase.invoke(query)) {
                is State.Success -> {
                    searchResult = result.data.toMutableList()
                }

                is State.Error -> {}
            }
            searchResult
        }
    }

    private fun getCategories(inputs: List<Int>) {
        viewModelScope.launch {
            val allCategories = mutableListOf<GetCategoryDTO>()
            val uniqueCategories = mutableSetOf<String>()

            inputs.forEach { input ->
                when (val result: State<List<GetCategoryDTO>> =
                    categoriesUseCase.invoke(input.toString())) {
                    is State.Success -> {
                        result.data.forEach { category ->
                            if (uniqueCategories.add(category.nameCategory)) {
                                allCategories.add(category)
                            }
                        }
                    }

                    is State.Error -> {}
                }
            }

            setState {
                ViewState.LoadCategories(allCategories)
            }
        }
    }


    sealed class ViewState : IViewState {
        data object Loading : ViewState()
        data class LoadCategories(val categories: List<GetCategoryDTO>) : ViewState()
        data class LoadCategoriesList(val categories: List<CategoryNameDTO>) : ViewState()
        data class SearchResults(val results: List<EstabelecimentoDTO>) : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object Loading : ViewEvent()
        data object LoadCategories : ViewEvent()
        data class Search(val query: String) : ViewEvent()
    }


}
