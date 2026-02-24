package com.example.bookmap.data.repository

import com.example.bookmap.data.models.Profile
import com.example.bookmap.data.models.UserRegisterDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    fun createUser(user: UserRegisterDataModel, onSuccess: () -> Unit, onFailure: () -> Unit) {
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener {
                val uid = auth.currentUser?.uid ?: run {
                    onFailure()
                    return@addOnSuccessListener
                }

                firestore.collection("users")
                    .document(uid)
                    .collection("profile")
                    .add(user.profile)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener {
                        onFailure()
                    }
            }
            .addOnFailureListener {
                onFailure()
            }
    }

    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure()
            }
    }

    fun loadUserProfile(
        uid: String,
        onSuccess: (Profile) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore
            .collection("users")
            .document(uid)
            .collection("profile")
            .limit(1)
            .get()
            .addOnSuccessListener { result ->
                val profile = result.documents.firstOrNull()?.toObject(Profile::class.java)
                if (profile != null) {
                    onSuccess(profile)
                } else {
                    onFailure(Exception("Perfil não encontrado"))
                }
            }
            .addOnFailureListener { e -> onFailure(e) }
    }


}
