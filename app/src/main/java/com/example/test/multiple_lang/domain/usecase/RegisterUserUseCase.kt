package com.example.test.multiple_lang.domain.usecase

import com.example.test.multiple_lang.domain.repository.UserRepository

class RegisterUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(username: String, email: String, password: String): Result<Unit> {
        if(repository.isUsernameTaken(username)) {
            return Result.failure(Exception("Username is exists"))
        }
        return repository.registerUser(username, email, password)
    }
}