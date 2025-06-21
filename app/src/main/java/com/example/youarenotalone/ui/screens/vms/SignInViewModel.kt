package com.example.youarenotalone.ui.screens.vms



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

var myUserId = mutableStateOf<Int?>(null)

class SignInViewModel : ViewModel() {
    var messageLogin = mutableStateOf("")
    var messagePassword = mutableStateOf("")

    fun login() {
        val client = OkHttpClient()
        val json = JSONObject()
        json.put("login", messageLogin.value)
        json.put("password", messagePassword.value)

        val requestBody = json.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("https://youarenotone.onrender.com/login")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("FLASK", "Ошибка: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val myId = try {
                    JSONObject(body ?: "{}").optString("myId", "нет id")
                } catch (e: Exception) {
                    e.printStackTrace()
                    "ошибка"
                }

                Log.d("FLASK", "myId: $myId")
                myUserId.value = myId.toInt()
            }
        })
        messageLogin.value = ""
        messagePassword.value = ""

    }

}