package ru.netology.nmedia.repository

import androidx.lifecycle.ViewModel

class PostViewModelRepository: ViewModel()
{

    private val postRepository: PostRepository = PostRepositoryMemory()

    var data = postRepository.getAll()

    fun likeById(id: Int) = postRepository.likeById(id)

    fun shareById(id: Int) =  postRepository.shareById(id)
}