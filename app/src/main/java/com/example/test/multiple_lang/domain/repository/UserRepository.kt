package com.example.test.multiple_lang.domain.repository

interface UserRepository {
    suspend fun isUsernameTaken(username: String): Boolean
    suspend fun registerUser(username: String, email: String, password: String): Result<Unit>
}