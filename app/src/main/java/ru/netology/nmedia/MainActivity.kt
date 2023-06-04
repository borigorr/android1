package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
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
        with(binding) {
            content.text = post.content
            likesCount.text = post.likeCount.toString()
            published.text = post.published
            author.text = post.author
            sharedCount.text = shareCountText(post.shareCount)
            if (post.likeByMe) {
                likes.setImageResource(R.drawable.like_active)
            }
            likes.setOnClickListener {
                post.likeByMe = !post.likeByMe
                if (post.likeByMe) {
                    post.likeCount++
                    likesCount.text = post.likeCount.toString()
                    likes.setImageResource(R.drawable.like_active)
                } else {
                    post.likeCount--
                    likes.setImageResource(R.drawable.baseline_favorite_border_24)
                    likesCount.text = post.likeCount.toString()
                }
            }
            sharedIcon.setOnClickListener{
                post.shareCount += 10
                sharedCount.text = shareCountText(post.shareCount)
            }

        }
    }

    private fun shareCountText(count: Int): String {
        if (count >= 1_000_000) {
            return  String.format("%.1fM", (count.toDouble() / 1_000_000.0))
        }
        if (count >= 1_000) {
            return  String.format("%.1fK", (count.toDouble() / 1_000.0))
        }
        return count.toString()
    }
}