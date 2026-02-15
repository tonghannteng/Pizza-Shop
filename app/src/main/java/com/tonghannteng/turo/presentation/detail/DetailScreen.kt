package com.tonghannteng.turo.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tonghannteng.turo.presentation.BusinessImage
import com.tonghannteng.turo.presentation.navigation.Route

@Composable
fun DetailScreen(
    modifier: Modifier,
    state: Route.DetailScreen
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(state.name)
        BusinessImage(
            modifier = modifier,
            imageUrl = state.url
        )
    }
}
