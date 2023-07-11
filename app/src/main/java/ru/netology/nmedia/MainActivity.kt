package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostViewModelRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModelRepository by viewModels()

        val adapter = PostAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }
        })

        viewModel.edited.observe(this) { post ->
            if (post.id == 0) {
                binding.editTexGroup.visibility = View.GONE
                return@observe
            }
            if (post.content.isNullOrBlank()) {
                binding.editTexGroup.visibility = View.GONE
            } else {
                binding.editTexGroup.visibility = View.VISIBLE
            }
            binding.editText.text = post.content

            with(binding.content) {
                requestFocus()
                setText(post.content)
            }
        }
        binding.closeEdit.setOnClickListener {
            binding.content.setText("")
            viewModel.changeContent("")
        }
        binding.save.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
            }
        }

        viewModel.data.observe(this) {
            adapter.submitList(it)
        }
        binding.list.adapter = adapter
    }
}