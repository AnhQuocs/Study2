package com.example.test.multiple_lang.data.repository

import android.util.Log
import com.example.test.model.User
import com.example.test.multiple_lang.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class FirebaseUserRepository : UserRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override suspend fun isUsernameTaken(username: String): Boolean {
        val result = db.collection("users")
            .whereEqualTo("username", username)
            .get()
            .await()
        return !result.isEmpty
    }

    override suspend fun registerUser(username: String, email: String, password: String): Result<Unit> {
        return try {
            Log.d("FirebaseUserRepository", "Register with email: '$email', password: '$password'")

            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid ?: return Result.failure(Exception("UID null"))

            delay(300)
            val user = User(
                uid = uid,
                username = username,
                email = email,
                createdAt = System.currentTimeMillis()
            )

            db.collection("users").document(uid).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}