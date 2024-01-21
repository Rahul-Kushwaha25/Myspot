package com.rahul.titan.myspot.utils
import com.google.firebase.auth.FirebaseAuth
import java.security.Timestamp
import java.text.SimpleDateFormat
import java.util.*
object firebaseutil {
    fun currentUserId(): String? {
        return FirebaseAuth.getInstance().uid
    }

    fun isLoggedIn(): Boolean {
        return currentUserId() != null
    }

    fun timeStampToString(timestamp: Timestamp): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(timestamp.timestamp)
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }




}