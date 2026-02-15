package com.tonghannteng.turo.presentation.main

sealed interface BusinessListEvent {
    data class Error(val error: String): BusinessListEvent
}