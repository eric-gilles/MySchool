package com.example.myschool.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Fonction pour connecter un utilisateur
    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()  // Callback pour indiquer le succès
                    } else {
                        onFailure(task.exception?.message ?: "Erreur inconnue")  // Callback pour indiquer l'échec
                    }
                }
        }
    }

    // Fonction pour enregistrer un nouvel utilisateur
    fun register(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()  // Callback pour indiquer le succès
                    } else {
                        onFailure(task.exception?.message ?: "Erreur inconnue")  // Callback pour indiquer l'échec
                    }
                }
        }
    }

    // Fonction pour se déconnecter
    fun logout() {
        auth.signOut()
    }

    // Vérifier si l'utilisateur est connecté
    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
