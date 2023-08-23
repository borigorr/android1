package ru.netology.nmedia.dao

import ru.netology.nmedia.dto.Post

interface PostDao {
    fun getById(id: Int): Post?
    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun likeById(id: Int)
    fun removeById(id: Int)
}