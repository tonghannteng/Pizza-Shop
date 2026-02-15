package com.tonghannteng.turo.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val total: Int? = null,
    val businesses: List<Business>,
    val region: Region? = null
)

@Serializable
data class Business(
    val id: String,
    val name: String? = null,
    @SerialName("image_url")
    val imageUrl: String? = null,
    val url: String? = null,
    @SerialName("review_count")
    val reviewCount: Int? = null,
    val categories: List<Category>? = null,
    val rating: Double? = null,
    val coordinates: Coordinates? = null,
    val transactions: List<String>? = null,
    val price: String? = null,
    val location: Location? = null,
    val phone: String? = null,
    @SerialName("display_phone")
    val displayPhone: String? = null,
    val distance: Double? = null,
)

@Serializable
data class Category(
    val alias: String,
    val title: String
)

@Serializable
data class Coordinates(
    val latitude: Double? = null,
    val longitude: Double? = null
)

@Serializable
data class Location(
    val address1: String? = null,
    val address2: String? = null,
    val address3: String? = null,
    val city: String? = null,
    @SerialName("zip_code")
    val zipCode: String? = null,
    val country: String? = null,
    val state: String? = null,
    @SerialName("display_address")
    val displayAddress: List<String>? = null
)

@Serializable
data class Region(
    val center: Center
)

@Serializable
data class Center(
    val latitude: Double,
    val longitude: Double
)
