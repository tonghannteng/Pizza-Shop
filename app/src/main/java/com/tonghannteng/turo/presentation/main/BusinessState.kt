package com.tonghannteng.turo.presentation.main

import com.tonghannteng.turo.data.Business

data class BusinessState(
    val businessList: List<Business> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)
