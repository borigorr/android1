package ru.netology.nmedia.repository

import androidx.lifecycle.ViewModel

class PostViewModelRepository: ViewModel()
{

    private val postRepository: PostRepository = PostRepositoryMemory()

    var data = postRepository.get()

    fun like() = postRepository.like()

    fun share() =  postRepository.share()
}