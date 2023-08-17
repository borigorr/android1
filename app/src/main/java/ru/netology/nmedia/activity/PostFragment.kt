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
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.helpers.NumberHelper
import ru.netology.nmedia.repository.PostViewModelRepository

class PostFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding

    private val viewModel: PostViewModelRepository by viewModels(
        ownerProducer = ::requireParentFragment
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        val id = arguments?.editId ?: 0
        val post = viewModel.getByIdOrEmpty(id)
        viewModel.viewPost.value = post
        viewModel.viewPost.observe(viewLifecycleOwner) {
            binding.includedPost.likes.text = NumberHelper.intToShortString(it.likeCount)
        }
        val holder = PostViewHolder(binding.includedPost, object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
                viewModel.viewPost.value = viewModel.getByIdOrEmpty(id)
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

            override fun onEdit(post: Post) {
                val bundle = Bundle().apply {
                    editText = post.content
                    editId = post.id
                    editLink = post.linkToVideo
                }
                findNavController().navigate(R.id.action_postFragment_to_editPostFragment, bundle)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
                findNavController().navigateUp()
            }

            override fun onClickVideo(post: Post) {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.linkToVideo))
                binding.root.context.startActivity(intent)
            }

            override fun onClickRoot(post: Post) {

            }

        })
        holder.bind(post)

        return binding.root
    }
}