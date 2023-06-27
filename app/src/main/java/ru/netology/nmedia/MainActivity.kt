package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.repository.PostViewModelRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModelRepository by viewModels()
        val adapter =  PostAdapter({
            viewModel.likeById(it.id)
        }) {
            viewModel.shareById(it.id)
        }
        viewModel.data.observe(this) {
            adapter.submitList(it)
        }
        binding.list.adapter = adapter
    }
}