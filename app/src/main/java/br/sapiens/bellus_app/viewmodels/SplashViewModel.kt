package br.sapiens.bellus_app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel(){
    private var splashShowFlow = MutableStateFlow(true)
    var isSplashShow = splashShowFlow.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000L)
            splashShowFlow.value = false
        }
    }
}