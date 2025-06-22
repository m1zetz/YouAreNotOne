package com.example.youarenotalone.ui.screens.vms

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

data class Stories(
    val user_id: Int,
    val text: String,
    val title: String

)

class StoriesViewModel : ViewModel() {

    var listOfStories = mutableStateListOf<Stories>()
    val client = OkHttpClient()
    var serverResponse = mutableStateOf("")

    fun getStories() {
        val request = Request.Builder()
            .url("https://youarenotone.onrender.com/get_all_posts")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("HTTP", "Ошибка: ${e.message}")
                serverResponse.value = "Ошибка подключения"
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                if (response.isSuccessful && body != null) {
                    try {
                        val jsonArray = JSONArray(body)

                        listOfStories.clear()

                        for (i in 0 until jsonArray.length()) {
                            val item = jsonArray.getJSONObject(i)
                            val title = item.getString("title")
                            val text = item.getString("post_text")

                            listOfStories.add(
                                Stories(
                                    user_id = -1,
                                    title = title,
                                    text = text
                                )
                            )
                        }

                        serverResponse.value = "Успешно загружено"
                    } catch (e: Exception) {
                        Log.e("HTTP", "Ошибка парсинга: ${e.message}")
                        serverResponse.value = "Ошибка при парсинге"
                    }
                } else {
                    serverResponse.value = "Ошибка от сервера"
                }
            }
        })
    }



}

