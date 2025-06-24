package com.example.youarenotalone.ui.screens.vms
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


import android.net.http.UrlRequest
import android.util.Log

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import io.ktor.client.request.request
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException
import org.json.JSONObject

import androidx.compose.runtime.mutableStateOf
import androidx.room.util.copy

var myUserId = mutableStateOf<Int?>(-1)

class SignInViewModel : ViewModel() {
    var messageLogin = mutableStateOf("")
    var messagePassword = mutableStateOf("")



    suspend fun login(): Int = withContext(Dispatchers.IO){

        val client = OkHttpClient()
        val json = JSONObject().apply {
            put("login", messageLogin.value)
            put("password", messagePassword.value)
        }

        val requestBody = json.toString()
            .toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("https://youarenotone.onrender.com/login")
            .post(requestBody)
            .build()

        try {
            val response = client.newCall(request).execute()
            val body = response.body?.string()
            val myId = JSONObject(body ?: "{}").optString("myId", "-2")
            Log.d("FLASK", "myId: $myId")

            myId.toIntOrNull() ?: -2
        } catch (e: Exception) {
            Log.e("LOGIN", "Ошибка при логине", e)
            -2
        } finally {
            messageLogin.value = ""
            messagePassword.value = ""
        }

    }
}