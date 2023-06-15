package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.trpository.PostViewModelRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModelRepository by viewModels()
        viewModel.data.observe(this) {
            with(binding) {
                content.text = it.content
                likesCount.text = it.likeCount
                published.text = it.published
                author.text = it.author
                sharedCount.text = it.shareCount
                viewCount.text = it.viewCount
                if (it.likeByMe) {
                    likes.setImageResource(R.drawable.like_active)
                } else {
                    likes.setImageResource(R.drawable.baseline_favorite_border_24)
                }
            }

        }
        binding.likes.setOnClickListener {
            viewModel.like()
        }
        binding.sharedIcon.setOnClickListener {
            viewModel.share()
        }
    }
}