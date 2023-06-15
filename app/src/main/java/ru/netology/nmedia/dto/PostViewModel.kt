package ru.netology.nmedia.dto

data class PostViewModel(
    val id: Int,
    val author: String,
    val content: String,
    val published: String,
    val likeCount: String,
    val shareCount: String,
    val viewCount: String,
    val likeByMe: Boolean = false,
)
