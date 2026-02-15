package com.tonghannteng.turo.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tonghannteng.turo.data.Business
import com.tonghannteng.turo.presentation.BusinessImage

@Composable
fun BusinessCard(
    modifier: Modifier = Modifier,
    business: Business
) {
    Card(modifier = modifier
        .fillMaxSize()
        .padding(2.dp)) {
        Text(
            text = business.name ?: "",
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1
        )
        Text(
            text = business.displayPhone ?: "",
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1
        )
        BusinessImage(
            modifier = Modifier.fillMaxSize(),
            imageUrl = business.imageUrl ?: ""
        )
    }
}
