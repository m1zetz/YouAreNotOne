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
import org.json.JSONObject
import java.io.IOException

class MyStoriesViewModel : ViewModel() {

    val client = OkHttpClient()
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

}