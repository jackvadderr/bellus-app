package br.sapiens.bellus_app.presentation.viewmodels

import android.util.Log
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
        Log.d("PesquisaViewModel", "Inicializando ViewModel")
        triggerEvent(ViewEvent.Loading)
    }

    override fun createInitialState(): ViewState {
        Log.d("PesquisaViewModel", "Criando estado inicial")
        return ViewState.Loading
    }

    override fun triggerEvent(event: ViewEvent) {
        Log.d("PesquisaViewModel", "Evento disparado: $event")
        when (event) {
            is ViewEvent.Loading -> {
                loadCategoriesList()
            }

            ViewEvent.LoadCategories -> {
                // Handle LoadCategories event
            }

            is ViewEvent.Search -> {
                // Handle Search event
            }
        }
    }

    private fun loadCategoriesList() {
        viewModelScope.launch {
            Log.d("PesquisaViewModel", "Carregando lista de categorias")
            when (val result: State<List<CategoryNameDTO>> = listCategoriesUseCase.invoke(null)) {
                is State.Success -> {
                    Log.d("PesquisaViewModel", "Categorias carregadas com sucesso: ${result.data}")
                    setState {
                        ViewState.LoadCategoriesList(result.data)
                    }
                }

                is State.Error -> {
                    Log.e("PesquisaViewModel", "Erro ao carregar categorias", result.exception)
                    setState {
                        ViewState.Error
                    }
                }
            }
        }
    }

    suspend fun getEstablishment(query: String): List<EstabelecimentoDTO> {
        Log.d("PesquisaViewModel", "Buscando estabelecimento com query: $query")
        return withContext(Dispatchers.IO) {
            val establishmentsId: List<SearchDTO> = getSearch(query)
            val establishments = mutableListOf<EstabelecimentoDTO>()
            establishmentsId.mapNotNull { searchDTO ->
                val id = searchDTO.establishmentId
                when (val result: State<EstabelecimentoDTO> = establishmentByIdUseCase.invoke(id)) {
                    is State.Success -> {
                        Log.d("PesquisaViewModel", "Estabelecimento encontrado: ${result.data}")
                        establishments.add(result.data)
                    }

                    is State.Error -> {
                        Log.e(
                            "PesquisaViewModel",
                            "Erro ao buscar estabelecimento",
                            result.exception
                        )
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
        Log.d("PesquisaViewModel", "Executando busca com query: $query")
        return withContext(Dispatchers.IO) {
            var searchResult = mutableListOf<SearchDTO>()
            when (val result: State<List<SearchDTO>> = searchUseCase.invoke(query)) {
                is State.Success -> {
                    Log.d("PesquisaViewModel", "Resultados da busca: ${result.data}")
                    searchResult = result.data.toMutableList()
                }

                is State.Error -> {
                    Log.e("PesquisaViewModel", "Erro ao executar busca", result.exception)
                }
            }
            searchResult
        }
    }

    private fun getCategories(inputs: List<Int>) {
        viewModelScope.launch {
            Log.d("PesquisaViewModel", "Carregando categorias com inputs: $inputs")
            val allCategories = mutableListOf<GetCategoryDTO>()
            val uniqueCategories = mutableSetOf<String>()

            inputs.forEach { input ->
                when (val result: State<List<GetCategoryDTO>> =
                    categoriesUseCase.invoke(input.toString())) {
                    is State.Success -> {
                        result.data.forEach { category ->
                            if (uniqueCategories.add(category.nameCategory)) {
                                Log.d(
                                    "PesquisaViewModel",
                                    "Categoria adicionada: ${category.nameCategory}"
                                )
                                allCategories.add(category)
                            }
                        }
                    }

                    is State.Error -> {
                        Log.e("PesquisaViewModel", "Erro ao carregar categoria", result.exception)
                    }
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
        data object Error : ViewState()
    }

    sealed class ViewEvent : IViewEvent {
        data object Loading : ViewEvent()
        data object LoadCategories : ViewEvent()
        data class Search(val query: String) : ViewEvent()
    }


}
