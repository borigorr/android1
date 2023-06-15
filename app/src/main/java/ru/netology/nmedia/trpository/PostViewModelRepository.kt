package ru.netology.nmedia.trpository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.PostViewModel
import ru.netology.nmedia.helpers.NumberHelper

class PostViewModelRepository: ViewModel()
{

    private val postRepository: PostRepository = PostRepositoryMemory()

    var data = MutableLiveData(get())

    fun like() {
        postRepository.like()
        data.value = get()
    }

    fun share() {
        postRepository.share()
        data.value = get()
    }

    fun get(): PostViewModel {
        val post = postRepository.get()
        return PostViewModel(
            id = post.id,
            author = post.author,
            content = post.content,
            published = post.published,
            likeCount =  NumberHelper.intToShortString(post.likeCount),
            shareCount = NumberHelper.intToShortString(post.shareCount),
            viewCount = NumberHelper.intToShortString(post.viewCount),
            likeByMe = post.likeByMe
        )
    }
}