package com.example.test.flights.data.source

import com.example.test.flights.data.model.FlightDto
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class FirebaseFlightDataSource {
    private val collection = Firebase.firestore.collection("flights")

    suspend fun fetchAllFlight(): List<Pair<String, FlightDto>> {
        return collection.get().await().map { doc ->
            doc.id to doc.toObject(FlightDto::class.java)
        }
    }
}