package com.example.myschool.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myschool.models.Course
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TeacherViewModel(private val teacherId: String) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        fetchCourses()
    }

    private fun fetchCourses() {
        db.collection("courses")
            .whereEqualTo("teacherId", teacherId)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    _errorMessage.value = "Error fetching courses: ${exception.message}"
                    return@addSnapshotListener
                }

                val courseList = snapshot?.documents?.mapNotNull { document ->
                    document.toObject(Course::class.java)?.copy(id = document.id)
                } ?: emptyList()

                _courses.value = courseList
            }
    }
}


