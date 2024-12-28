package com.example.myschool.models

data class User(
    val id: String = "",
    val name: String = "",
    val firstname: String = "",
    val email: String = "",
    val userType: UserType = UserType.STUDENT, // Enum pour Parent ou Élève ou Enseignant
    val links: List<String> = emptyList(), // Liste des liens Twitter, Facebook, etc.
    val children: List<User> = emptyList()  // Liste des enfants de l'utilisateur (pour les parents)
)


enum class UserType {
    PARENT,
    STUDENT,
    TEACHER
}
