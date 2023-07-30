package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostEdit
import ru.netology.nmedia.repository.PostViewModelRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModelRepository by viewModels()

        val editPostLauncher = registerForActivityResult(EditPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            val content = result.content.let { it ?: "" }
            val id = result.id
            var post = viewModel.getByIdOrEmpty(id).copy(content = content, linkToVideo = result.link)
            viewModel.edit(post)
            viewModel.save()
        }

        val adapter = PostAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                editPostLauncher.launch(PostEdit(id = post.id, content = post.content, link = post.linkToVideo))
            }

            override fun onClickVideo(post: Post) {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.linkToVideo))
                binding.root.context.startActivity(intent)
            }
        })

        viewModel.data.observe(this) {
            adapter.submitList(it)
        }
        binding.list.adapter = adapter



        binding.add.setOnClickListener {
            editPostLauncher.launch(PostEdit())
        }
    }
}