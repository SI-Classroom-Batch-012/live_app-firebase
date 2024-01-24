package com.example.firebaselive.model

import com.google.firebase.Timestamp

data class Note (
    val title: String = "",
    val content: String = "",
    val timestamp: Timestamp = Timestamp.now()
)