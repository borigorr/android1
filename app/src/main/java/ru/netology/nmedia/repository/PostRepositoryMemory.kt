package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryMemory: PostRepository {

    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий",
        published = "2020.01.01",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по " +
                "онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и " +
                "управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных " +
                "профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже " +
                "есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. " +
                "Наша миссия — помочь встать на путь роста и начать цепочку перемен → " +
                "http://netolo.gy/fyb"
    )

    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        var likeCount = this.post.likeCount
        if (this.post.likeByMe) {
            likeCount--
        } else {
            likeCount++
        }
        this.post = post.copy(likeByMe = !this.post.likeByMe, likeCount = likeCount)
        this.data.value = this.post
    }

    override fun share() {
        this.post = post.copy(shareCount = post.shareCount + 1)
        this.data.value = this.post
    }
}