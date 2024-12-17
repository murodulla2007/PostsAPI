package com.example.postapi.networking

import com.example.postapi.model.Posts
import com.example.postapi.model.PostsItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object Retrofit {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }


}

interface ApiService {
    @GET("/posts")
    fun getPosts(): Call<Posts>

    @GET("posts/{id}")
    fun getPostById(@Path("id") id: Int): Call<PostsItem>

}