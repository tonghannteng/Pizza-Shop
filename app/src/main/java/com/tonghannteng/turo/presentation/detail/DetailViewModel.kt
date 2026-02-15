package com.tonghannteng.turo.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.tonghannteng.turo.data.Business
import com.tonghannteng.turo.presentation.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val business = savedStateHandle.toRoute<Route.DetailScreen>()

    private val _state = MutableStateFlow(business)
    val state = _state.asStateFlow()

}
