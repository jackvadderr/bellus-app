package br.sapiens.bellus_app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.sapiens.bellus_app.telas.home.BottomNavItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TelaNavegationBarViewModel @Inject constructor(): ViewModel() {

    private val _selectedItem = MutableLiveData<BottomNavItem>()
    val selectedItem: LiveData<BottomNavItem> get() = _selectedItem

    fun selectItem(item: BottomNavItem) {
        _selectedItem.value = item
    }
}