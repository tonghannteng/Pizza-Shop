package com.tonghannteng.turo.presentation.main

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tonghannteng.turo.data.Business
import kotlinx.coroutines.flow.Flow

@Composable
fun MainScreen(
    modifier: Modifier,
    state: BusinessState,
    onItemClick: (Business) -> Unit,
    textValue: String,
    onValueChange: (String) -> Unit,
    event: Flow<BusinessListEvent>,
) {

    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        event.collect { event ->
            when (event) {
                is BusinessListEvent.Error -> {
                    Toast.makeText(context, event.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column {

        TextField(
            modifier = modifier.fillMaxWidth(),
            value = textValue,
            onValueChange = { onValueChange(it) },
            label = { Text("Search...") }
        )

        Spacer(modifier = Modifier.height(12.dp))
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        } else {
            LazyColumn(
                modifier = modifier.fillMaxWidth()
            ) {
                items(
                    items = state.businessList,
                    key = { result -> result.id }
                ) { business ->
                    BusinessCard(
                        modifier = Modifier.clickable { onItemClick(business) },
                        business = business
                    )
                }
            }
        }

    }

}
