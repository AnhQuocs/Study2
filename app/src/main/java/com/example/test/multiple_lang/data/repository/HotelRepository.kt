package com.example.test.multiple_lang.data.repository

import com.example.test.multiple_lang.Hotel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class HotelRepository (
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun getHotels(): List<Hotel> {
        val snapshot = firestore.collection("hotels").get().await()
        return snapshot.documents.mapNotNull { it.toObject(Hotel::class.java) }
    }
}