package com.example.todoapp.components

import android.content.Context
import android.content.SharedPreferences

class IdManager(context: Context) {
    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveId(userId: String) {
        prefs.edit().putString("userId", userId).apply()
    }

    fun getId(): String? {
        return prefs.getString("userId", null)
    }

    fun clearId() {
        prefs.edit().remove("userId").apply()
    }
}