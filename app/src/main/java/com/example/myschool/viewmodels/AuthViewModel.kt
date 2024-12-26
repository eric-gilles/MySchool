package com.example.myschool.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
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

    // Fonction pour inscrire un utilisateur
    fun register(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        // Basic input validation
        if (email.isBlank()) {
            onFailure("L'adresse e-mail ne peut pas être vide")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            onFailure("Adresse e-mail invalide")
            return
        }
        if (password.length < 6) {
            onFailure("Le mot de passe doit contenir au moins 6 caractères")
            return
        }

        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()  // Notify success
                    } else {
                        val errorMessage = when (task.exception) {
                            is FirebaseAuthWeakPasswordException -> "Mot de passe trop faible"
                            is FirebaseAuthInvalidCredentialsException -> "Adresse e-mail invalide"
                            is FirebaseAuthUserCollisionException -> "Un compte avec cet e-mail existe déjà"
                            else -> task.exception?.message ?: "Erreur inconnue"
                        }
                        onFailure(errorMessage)  // Notify failure with detailed message
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


    // Fonction pour envoyer un e-mail de réinitialisation du mot de passe
    fun sendPasswordResetEmail(email: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if (email.isBlank()) {
            onFailure("L'adresse e-mail ne peut pas être vide")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            onFailure("Adresse e-mail invalide")
            return
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onFailure(task.exception?.message ?: "Erreur inconnue")
            }
        }
    }

    // Fonction pour envoyer un e-mail de vérification suite à l'inscription
    fun sendEmailVerification(email: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if (email.isBlank()) {
            onFailure("L'adresse e-mail ne peut pas être vide")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            onFailure("Adresse e-mail invalide")
            return
        }

        auth.currentUser?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onFailure(task.exception?.message ?: "Erreur inconnue")
            }
        }
    }
}
