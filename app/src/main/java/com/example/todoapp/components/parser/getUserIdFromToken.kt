package com.example.todoapp.components.parser

import android.util.Base64
import org.json.JSONObject

fun getUserIdFromToken(token: String): String? {
    return try {
        val parts = token.split(".")
        if (parts.size < 2) return null

        val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
        val json = JSONObject(payload)
        json.getString("sub") // может быть email или userId
    } catch (e: Exception) {
        null
    }
}
