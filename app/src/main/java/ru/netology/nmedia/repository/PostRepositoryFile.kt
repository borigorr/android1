package ru.netology.nmedia.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositoryFile(
    private val context: Context,
) : PostRepository {

    private val gson = Gson()
    private var nextId: Int
    private val filename = "posts.json"
    init {
        val file = context.filesDir.resolve(filename)
        if (!file.exists()) {
            context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
                it.write(gson.toJson(emptyList<Post>()))
            }
        }
        nextId = (posts.maxOfOrNull { it.id } ?: 0) + 1
    }

    private val data = MutableLiveData(posts)
    private var posts: List<Post>
        get() {
            return context.openFileInput(filename).bufferedReader().use {
                gson.fromJson(
                    it,
                    TypeToken.getParameterized(List::class.java, Post::class.java).type
                )
            }
        }
        set(value) {
            context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
                it.write(gson.toJson(value))
            }
        }
    override fun getAll(): LiveData<List<Post>> = data
    override fun getById(id: Int): Post? {
        return posts.firstOrNull { it.id == id }
    }

    override fun likeById(id: Int) {
        posts = posts.map {
            if (it.id == id) {
                it.copy(
                    likeByMe = !it.likeByMe,
                    likeCount = if (it.likeByMe) it.likeCount - 1 else it.likeCount + 1
                )
            } else {
                it
            }
        }
        this.data.value = posts

    }

    override fun removeById(id: Int) {
        posts = this.posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == 0) {
            var newPosts = listOf(post.copy(id = nextId++))
            posts = newPosts + posts
            data.value = posts
            return
        }
        posts = this.posts.map {
            if (it.id == post.id) {
                post.copy(content = post.content, linkToVideo = post.linkToVideo)
            } else {
                it
            }
        }
        data.value = posts
    }

    override fun shareById(id: Int) {
        posts = posts.map {
            if (it.id == id) {
                it.copy(shareCount = it.shareCount + 1)
            } else {
                it
            }
        }
        this.data.value = posts

    }
}