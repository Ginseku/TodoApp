package com.example.todoapp.screens.Autentification.network

import com.example.todoapp.API.TaskApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080") // эмулятор Android, меняй на свой IP
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val api: AuthApi = retrofit.create(AuthApi::class.java)
    val taskApi: TaskApi = retrofit.create(TaskApi::class.java)
}
