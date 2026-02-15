package com.tonghannteng.turo.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MainRepositoryImpl(
    private val httpClient: HttpClient
): MainRepository {

    override suspend fun getPizza(): SearchResponse {
        return try {
            httpClient.get("/v3/businesses/search") {
                parameter("term", "pizza")
                parameter("location", "111 Sutter Street San Francisco, CA")
            }.body()
        } catch (e: Exception) {
            e.printStackTrace()
            SearchResponse(businesses = emptyList())
        }

    }

    override suspend fun getBeer(): SearchResponse {
        return try {
            httpClient.get("/v3/businesses/search") {
                parameter("term", "beer")
                parameter("location", "111 Sutter Street San Francisco, CA")
            }.body()
        } catch (e: Exception) {
            e.printStackTrace()
            SearchResponse(businesses = emptyList())
        }

    }
}
