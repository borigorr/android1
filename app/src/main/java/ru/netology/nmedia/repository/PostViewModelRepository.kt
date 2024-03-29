package ru.netology.nmedia.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post


private val empty = Post(
    id = 0,
    content = "",
    author = "Me",
    likeByMe = false,
    published = "Now"
)

class PostViewModelRepository(context: Application) : AndroidViewModel(context) {

    val edited = MutableLiveData(empty)

    val viewPost = MutableLiveData(empty)

    private val postRepository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(context = context).postDao()
    )

    var data = postRepository.getAll()

    fun getByIdOrEmpty(id: Int) = postRepository.getById(id) ?: empty.copy()

    fun likeById(id: Int) {
        postRepository.likeById(id)
        if (viewPost.value?.id == id) {
            viewPost.value = postRepository.getById(id) ?: empty
        }
    }

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

    fun shareById(id: Int)
    {
        postRepository.shareById(id)
        if (viewPost.value?.id == id) {
            viewPost.value = postRepository.getById(id) ?: empty
        }
    }

    fun save() {
        edited.value?.let {
            postRepository.save(it)
        }
        edited.value = empty
    }
}