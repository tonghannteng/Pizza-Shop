package com.tonghannteng.turo.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object HomeScreen: Route
    @Serializable
    data class DetailScreen(val name: String, val url: String): Route
}
