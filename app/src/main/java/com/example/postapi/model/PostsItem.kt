package com.example.postapi.model

data class PostsItem(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)