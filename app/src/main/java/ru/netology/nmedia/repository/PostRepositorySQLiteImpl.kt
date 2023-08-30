package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostEntity

class PostRepositorySQLiteImpl (
    private val dao: PostDao
) : PostRepository {
    private var posts = dao.getAll().map { it.toDto() }
    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun getById(id: Int): Post? {
        return dao.getById(id)?.toDto()
    }

    override fun likeById(id: Int) {
        dao.likeById(id)
        posts = dao.getAll().map { it.toDto() }
        data.value = posts
    }

    override fun removeById(id: Int) {
        dao.removeById(id)
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
        data.value = dao.getAll().map { it.toDto() }
    }

    override fun shareById(id: Int) {
        dao.shareById(id)
        posts = dao.getAll().map { it.toDto() }
        data.value = posts
    }

}