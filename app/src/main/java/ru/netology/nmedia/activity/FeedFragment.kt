package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.MainActivity.Companion.editId
import ru.netology.nmedia.activity.MainActivity.Companion.editLink
import ru.netology.nmedia.activity.MainActivity.Companion.editText
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostViewModelRepository

class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding

    private val viewModel: PostViewModelRepository by viewModels(
        ownerProducer = ::requireParentFragment
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = PostAdapter(object : OnInteractionListener {

            override fun onClickRoot(post: Post) {
                val bundle = Bundle().apply {
                    editId = post.id
                }
                findNavController().navigate(R.id.action_feedFragment_to_postFragment, bundle)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
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
                val bundle = Bundle().apply {
                    editText = post.content
                    editId = post.id
                    editLink = post.linkToVideo
                }
                findNavController().navigate(R.id.action_feedFragment_to_editPostFragment, bundle)
            }

            override fun onClickVideo(post: Post) {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.linkToVideo))
                binding.root.context.startActivity(intent)
            }
        })

        viewModel.data.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.list.adapter = adapter

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_editPostFragment)
        }

        return binding.root
    }
}