package ru.netology.nmedia.dto

data class Post(
    val id: Int,
    val author: String,
    val content: String,
    val published: String,
    var likeCount: Int = 0,
    var shareCount: Int = 990,
    var likeByMe: Boolean = false,
)
