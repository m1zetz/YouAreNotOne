package com.example.youarenotalone.ui.screens.vms


import android.util.Log
import android.view.View
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

class MyStoriesViewModel : ViewModel() {

    val client = OkHttpClient()

    var serverResponse = mutableStateOf("")

    var listOfMyStories = mutableStateListOf<Stories>()

    var stateOfBottomSheet = mutableStateOf(false)

    var titleStory = mutableStateOf("")

    var textStory = mutableStateOf("")


    fun addPost(user_id: Int, title: String, text: String){

        val json = JSONObject()

        json.put("user_id", user_id)
        json.put("text", text )
        json.put("title", title )

        val requestBody = json.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url("https://youarenotone.onrender.com/add_post")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("HTTP", "Ошибка: ${e.message}")
            }
            override fun onResponse(call: Call, response: Response) {
                Log.d("HTTP", "Успех: ${response.code}")
            }
        })

        titleStory.value = ""
        textStory.value = ""


    }

    fun getMyStories() {
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

                        listOfMyStories.clear()

                        val currentUserId = myUserId.value

                        for (i in 0 until jsonArray.length()) {
                            val item = jsonArray.getJSONObject(i)
                            val title = item.getString("title")
                            val text = item.getString("post_text")
                            val user_idServer = item.getInt("user_id")
                            val post_id = item.getInt("id")

                            if (currentUserId != null){

                                if(user_idServer == currentUserId){
                                    listOfMyStories.add(
                                        Stories(
                                            user_id = user_idServer,
                                            title = title,
                                            text = text,
                                            post_id = post_id

                                        )
                                    )
                                }

                            }

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