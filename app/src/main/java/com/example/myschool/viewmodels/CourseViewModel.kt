package com.example.myschool.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myschool.models.Course
import com.google.firebase.firestore.FirebaseFirestore

class CourseViewModel : ViewModel() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _courses = MutableLiveData<List<Course>>()
    val courses: LiveData<List<Course>> get() = _courses

    // Fonction pour récupérer les cours depuis Firestore
    fun fetchCourses() {
        db.collection("courses")
            .get()
            .addOnSuccessListener { result ->
                val courseList = result.map { document ->
                    Course(
                        id = document.id,
                        title = document.getString("title") ?: "",
                        description = document.getString("description") ?: "",
                        teacherId = document.getString("teacherId") ?: ""
                    )
                }
                _courses.value = courseList
            }
            .addOnFailureListener {
                // Gérer l'erreur ici
                _courses.value = emptyList() // Ou gérer l'erreur de manière appropriée
            }
    }
}
