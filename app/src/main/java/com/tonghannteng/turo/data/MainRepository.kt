package com.tonghannteng.turo.data

interface MainRepository {
    suspend fun getPizza(): SearchResponse
    suspend fun getBeer(): SearchResponse
}
