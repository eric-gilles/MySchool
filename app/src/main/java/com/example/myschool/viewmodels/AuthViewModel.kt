package com.example.myschool.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myschool.models.User
import com.example.myschool.models.UserType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance() // Instance de Firebase Auth
    private val firestore = FirebaseFirestore.getInstance()  // Instance de Firestore


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
    fun registerParent(
        name: String,
        firstname: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        // Input validation
        if (!validateInput(name, firstname, email, password)) {
            onFailure("Veuillez remplir tous les champs correctement")
            return
        }

        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener // Get user ID or return
                    val parentUser = User(
                            id = userId,
                            name = name,
                            firstname = firstname,
                            email = email,
                            userType = UserType.PARENT,
                            links = emptyList(),
                            children = emptyList()
                    )
                    // save user data to Firestore
                    firestore.collection("users").document(userId).set(parentUser)
                        .addOnSuccessListener {
                            onSuccess()  // Notify success
                        }
                        .addOnFailureListener {
                            onFailure(it.message ?: "Erreur de l'inscription")
                        }
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

    private fun validateInput(name: String, firstname: String, email: String, password: String): Boolean {
        if (name.isBlank() || firstname.isBlank() || email.isBlank() || password.isBlank()) {
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false
        }
        if (password.length < 6) {
            return false
        }
        return true
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

    // Function to get the current user ID
    fun getCurrentUserId(): String {
        return auth.currentUser?.uid ?: throw IllegalStateException("No user is currently logged in")
    }

    // Function to fetch user data from Firestore
    fun fetchUserData(
        userId: String,
        onSuccess: (User) -> Unit,
        onFailure: (String) -> Unit
    ) {
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(User::class.java)
                    if (user != null) {
                        onSuccess(user)
                    } else {
                        onFailure("Unable to parse user data")
                    }
                } else {
                    onFailure("User not found in Firestore")
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception.message ?: "An unknown error occurred")
            }
    }
}
