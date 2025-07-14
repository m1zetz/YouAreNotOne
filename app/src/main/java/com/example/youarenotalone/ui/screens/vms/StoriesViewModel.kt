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
    val post_id: Int,
    val text: String,
    val title: String
)

data class Comments(
    val comment_id: Int,
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

    //-----------------------------------LIKES COMMENTS_________________


    fun getLikesComments(comment_id: Int, user_id: Int, callback: (Int?, List<Int>) -> Unit){
        var json = JSONObject()
        json.put("comment_id", comment_id)
        json.put("user_id", user_id)

        val requestBody = json.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("https://youarenotone.onrender.com/get_likes_comments")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("HTTP", "Ошибка: ${e.message}")
                callback(-1, emptyList())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val response = JSONObject(body)
                val count = response.getInt("count")

                val likedByJsonArray = response.getJSONArray("liked_by")
                val likedBy = mutableListOf<Int>()
                for (i in 0 until likedByJsonArray.length()) {
                    likedBy.add(likedByJsonArray.getInt(i))
                }

                callback(count, likedBy)
            }

        })

    }

    fun dropLikeComment(comment_id: Int,user_id: Int){
        var json = JSONObject()
        json.put("comment_id", comment_id)
        json.put("user_id", user_id)

        val requestBody = json.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("https://youarenotone.onrender.com/drop_like_comment")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("HTTP", "Ошибка: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("HTTP", "Лайк убран успешно")
                } else {
                    Log.e("HTTP", "Ошибка ответа: ${response.code}")
                }
            }

        })
    }

    fun addLikeComment(comment_id: Int, user_id: Int?){
        var json = JSONObject()
        json.put("comment_id", comment_id)
        json.put("user_id", user_id)

        val requestBody = json.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("https://youarenotone.onrender.com/add_like_comment")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("HTTP", "Ошибка: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("HTTP", "Лайк добавлен успешно")
                } else {
                    Log.e("HTTP", "Ошибка ответа: ${response.code}")
                }
            }

        })
    }




    // --------------------------------LIKES POSTS___________________
    fun getLikes(post_id: Int, user_id: Int, callback: (Int?, List<Int>) -> Unit){
        var json = JSONObject()
        json.put("post_id", post_id)
        json.put("user_id", user_id)

        val requestBody = json.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("https://youarenotone.onrender.com/get_likes")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("HTTP", "Ошибка: ${e.message}")
                callback(-1, emptyList())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val response = JSONObject(body)
                val count = response.getInt("count")

                val likedByJsonArray = response.getJSONArray("liked_by")
                val likedBy = mutableListOf<Int>()
                for (i in 0 until likedByJsonArray.length()) {
                    likedBy.add(likedByJsonArray.getInt(i))
                }

                callback(count, likedBy)
            }

        })

    }

    fun dropLike(post_id: Int,user_id: Int){
        var json = JSONObject()
        json.put("post_id", post_id)
        json.put("user_id", user_id)

        val requestBody = json.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("https://youarenotone.onrender.com/drop_like")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("HTTP", "Ошибка: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("HTTP", "Лайк убран успешно")
                } else {
                    Log.e("HTTP", "Ошибка ответа: ${response.code}")
                }
            }

        })
    }

    fun addLike(post_id: Int, user_id: Int?){
        var json = JSONObject()
        json.put("post_id", post_id)
        json.put("user_id", user_id)

        val requestBody = json.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("https://youarenotone.onrender.com/add_like")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("HTTP", "Ошибка: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("HTTP", "Лайк добавлен успешно")
                } else {
                    Log.e("HTTP", "Ошибка ответа: ${response.code}")
                }
            }

        })
    }


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

                            val comment_id = item.getInt("comment_id")
                            val post_id = item.getInt("post_id")
                            val comment = item.getString("comments")

                            comments.add(
                                Comments(
                                    comment_id = comment_id,
                                    post_id = post_id,
                                    comment = comment
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

