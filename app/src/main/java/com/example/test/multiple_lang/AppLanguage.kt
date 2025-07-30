package com.example.test.multiple_lang

enum class AppLanguage(val code: String) {
    EN("en"),
    VI("vi");

    companion object {
        fun fromCode(code: String): AppLanguage {
            return values().find { it.code == code } ?: EN
        }
    }
}