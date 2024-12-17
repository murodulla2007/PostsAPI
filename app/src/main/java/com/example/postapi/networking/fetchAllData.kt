package com.example.postapi.networking

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.postapi.model.Posts
import com.example.postapi.model.PostsItem

import retrofit2.Callback
import retrofit2.Response

@Composable
fun fetchAllData(): List<PostsItem> {
    var postsList: List<PostsItem> by remember { mutableStateOf(emptyList()) }
    val posts = Retrofit.api.getPosts()
    posts.enqueue(object : Callback<Posts> {
        override fun onResponse(
            call: retrofit2.Call<Posts>,
            response: Response<Posts>
        ) {
            if (response.isSuccessful) {
                val postsResponse = response.body()
                postsResponse?.let {
                    postsList = it
                    Log.d("TAGbek", "onResponse: ${postsList.size}")
                }
            } else {
                println("Error: ${response.code()} - ${response.message()}")
            }
        }

        override fun onFailure(call: retrofit2.Call<Posts>, t: Throwable) {
            Log.d("TAGbekFETCH", "onFailure: failed ${t.message}")
        }
    })
    return postsList

}