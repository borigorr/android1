package ru.netology.nmedia.dto

data class Post(
    val id: Int,
    val author: String,
    val content: String,
    val published: String,
    val likeCount: Int = 0,
    val linkToVideo: String? = null,
    val shareCount: Int = 0,
    val viewCount: Int = 0,
    val likeByMe: Boolean = false,
)
