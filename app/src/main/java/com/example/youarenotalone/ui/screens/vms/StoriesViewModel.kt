package com.example.youarenotalone.ui.screens.vms

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import java.util.concurrent.TimeUnit

data class Stories(
    val user_id: Int,
    val post_id: Int,
    val text: String,
    val title: String
)

data class Comments(
    val post_id: Int,
    val comment: String,
)

class StoriesViewModel : ViewModel() {

    var currentPostId = mutableStateOf<Int?>(null)
    var currentPostTitle = mutableStateOf("")
    var currentPostText = mutableStateOf("")

    var comment by mutableStateOf("")


    var comments =  mutableStateListOf<Comments>()


    var listOfStories = mutableStateListOf<Stories>()
    val client = OkHttpClient()
    var serverResponse = mutableStateOf("")

    fun getComments(){
        val request = Request.Builder()
            .url("https://youarenotone.onrender.com/get_comments")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("HTTP", "Ошибка при загрузке комментариев: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                var body = response.body?.string()
                if(response.isSuccessful){
                    try {
                        val jsonArray = JSONArray(body)

                        comments.clear()

                        for (i in 0 until jsonArray.length()) {
                            val item = jsonArray.getJSONObject(i)

                            val post_id = item.getInt("post_id")
                            val comment = item.getString("comments")

                            comments.add(
                                Comments(
                                    post_id = post_id,
                                    comment = comment
                                )
                            )
                            Log.d("comments", "Загруженные комментарии: $comments")
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


    fun sendComment(comment: String, postId: Int){

        var json = JSONObject()
        json.put("comment", comment)
        json.put("postId", postId)

        val body = json.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("https://youarenotone.onrender.com/add_comment")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("HTTP", "Ошибка: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("HTTP", "Успех: ${response.code}")
                getComments()
            }

        })

        this.comment = ""
    }



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
                            val post_id = item.getInt("id")

                            listOfStories.add(
                                Stories(
                                    user_id = -1,
                                    title = title,
                                    text = text,
                                    post_id = post_id
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

