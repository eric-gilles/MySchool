package com.example.myschool.models

data class User(
    val id: String,
    val name: String,
    val email: String,
    val userType: UserType // Enum pour Parent ou Élève
)

enum class UserType {
    PARENT,
    STUDENT
}
