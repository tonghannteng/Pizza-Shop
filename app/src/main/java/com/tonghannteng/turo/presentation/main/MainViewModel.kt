package com.tonghannteng.turo.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonghannteng.turo.data.Business
import com.tonghannteng.turo.data.MainRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _result = MutableStateFlow(BusinessState())
//    val result = _result.asStateFlow()

    private val _events = MutableSharedFlow<BusinessListEvent>()
    val events = _events.asSharedFlow()

    private val _textValue = MutableStateFlow("")
    val textValue = _textValue.asStateFlow()

    val result: StateFlow<BusinessState> = combine(_result, _textValue) { state, query ->
        if (query.isEmpty()) {
            val allList = state.businessList
            state.copy(businessList = allList)
        } else {
            val filter = state.businessList.filter { business ->
                business.name!!.contains(query, ignoreCase = true)
            }
            state.copy(businessList = filter)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BusinessState()
    )

    init {
        getAllPizzaAndBeer()
    }

    fun getAllPizzaAndBeer() {
        viewModelScope.launch {
            _result.update { it.copy(isLoading = true) }
            val result = fetchPizzaAndBeer()
            _result.update {
                it.copy(
                    isLoading = false,
                    businessList = result
                )
            }
        }
    }

    private suspend fun fetchPizzaAndBeer(): List<Business> = supervisorScope {
        val pizzaDeferred = async { mainRepository.getPizza().businesses }
        val beerDeferred = async { mainRepository.getBeer().businesses }

        val pizzaResult = runCatching { pizzaDeferred.await() }
        val beerResult = runCatching { beerDeferred.await() }

        if (pizzaResult.isFailure) {
            //Log.d("LogLogLog", "Pizza API Failed")
            _events.emit(BusinessListEvent.Error("Pizza API Failed"))
        }

        if (beerResult.isFailure) {
            //Log.d("LogLogLog", "Beer API Failed")
            _events.emit(BusinessListEvent.Error("Beer API Failed"))
        }

        val pizza = pizzaResult.getOrElse { emptyList() }
        val beer = beerResult.getOrElse { emptyList() }

        return@supervisorScope (pizza + beer)
    }

    fun onValueChange(text: String) {
        _textValue.update { text }
    }

    companion object {
        private const val LOG = "MainViewModelLog"
    }
}
