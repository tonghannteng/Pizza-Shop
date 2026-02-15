package com.tonghannteng.turo.data

import com.tonghannteng.turo.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {

    fun create() = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }

        defaultRequest {
            url(urlString = URL)
            header("accept", "application/json")
            header("authorization", "Bearer ${BuildConfig.API_KEY}")
        }
    }

}

private const val URL = "https://api.yelp.com"
