package com.example.myschool.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myschool.models.Progress
import com.google.firebase.firestore.FirebaseFirestore

class ProgressViewModel : ViewModel() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _progressList = MutableLiveData<List<Progress>>()
    val progressList: LiveData<List<Progress>> get() = _progressList

    // Fonction pour récupérer le progrès d'un élève depuis Firestore
    fun fetchProgress(userId: String) {
        db.collection("progress")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                val progressData = result.map { document ->
                    Progress(
                        courseId = document.getString("courseId") ?: "",
                        userId = document.getString("userId") ?: "",
                        score = document.getDouble("score") ?: 0.0
                    )
                }
                _progressList.value = progressData
            }
            .addOnFailureListener {
                // Gérer l'erreur ici
                _progressList.value = emptyList() // Ou gérer l'erreur de manière appropriée
            }
    }
}
