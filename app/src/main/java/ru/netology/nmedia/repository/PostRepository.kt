package ru.netology.nmedia.repository

import android.app.UiAutomation
import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {

    fun getAll(): LiveData<List<Post>>

    fun likeById(id: Int)

    fun shareById(id: Int)
}