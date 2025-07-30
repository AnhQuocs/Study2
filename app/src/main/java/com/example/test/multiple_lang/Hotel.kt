package com.example.test.multiple_lang

data class Hotel(
    val hotelId: String = "",
    val name: Map<String, String> = emptyMap(),
    val address: String = "",
    val shortAddress: String = "",
    val city: String = "",
    val country: String = "",
    val description: Map<String, String> = emptyMap(),
    val thumbnailUrl: String = "",
    val imageUrl: List<String> = emptyList(),
    val amenities: Map<String, List<String>> = emptyMap(),
    val pricePerNightMin: Int = 0,
    val averageRating: Double = 0.0,
    val numberOfReviews: Int = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val checkInTime: String = "",
    val checkOutTime: String = ""
)

fun Map<String, String>.localized(lang: String): String =
    this[lang] ?: this["en"] ?: ""

fun Map<String, List<String>>.localizedList(lang: String): List<String> =
    this[lang] ?: this["en"] ?: emptyList()