package ru.netology.nmedia.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post


private val empty = Post(
    id = 0,
    content = "",
    author = "Me",
    likeByMe = false,
    published = "Now"
)
class PostViewModelRepository: ViewModel()
{

    val edited =  MutableLiveData(empty)

    private val postRepository: PostRepository = PostRepositoryMemory()

    var data = postRepository.getAll()

    fun likeById(id: Int) = postRepository.likeById(id)

    fun shareById(id: Int) =  postRepository.shareById(id)

    fun removeById(id: Int) = postRepository.removeById(id)

    fun edit(post: Post) {
        edited.value = post
    }
    fun changeContent(content: String) {
        val text = content.trim()
        edited.value = edited.value?.copy(content = text)
    }

    fun cancelEdit() {
        this.edited.value = empty.copy()
    }

    fun save() {
        edited.value?.let {
            postRepository.save(it)
        }
        edited.value = empty
    }
}