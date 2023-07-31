package ru.netology.nmedia.adapter

import android.content.Intent
import android.net.Uri
import android.widget.PopupMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.helpers.NumberHelper

interface OnInteractionListener {
    fun onLike(post: Post)

    fun onShare(post: Post)

    fun onEdit(post: Post)

    fun onRemove(post: Post)

    fun onClickVideo(post: Post)
}

class PostAdapter(private val interactionListener: OnInteractionListener) :
    ListAdapter<Post, PostViewHolder>(PostDiffUntil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = this.currentList[position]
        holder.bind(post)
    }

}

class PostViewHolder(

    private val binding: CardPostBinding,
    private val interactionListener: OnInteractionListener
) : ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            viewCount.text = NumberHelper.intToShortString(post.viewCount)
            shared.text = NumberHelper.intToShortString(post.shareCount)
            likes.text = NumberHelper.intToShortString(post.likeCount)
            likes.isChecked = post.likeByMe
            post.linkToVideo.let {url ->
                if (url.isNullOrEmpty()) {
                    video.visibility = View.GONE
                    return return@let
                }
                video.visibility = View.VISIBLE
                video.setOnClickListener {
                    interactionListener.onClickVideo(post)
                }
            }
            likes.setOnClickListener() {
                interactionListener.onLike(post)
            }
            shared.setOnClickListener() {
                interactionListener.onShare(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.remove -> {
                                interactionListener.onRemove(post)
                                true
                            }

                            R.id.edit -> {
                                interactionListener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}

class PostDiffUntil() : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.equals(newItem)
    }

}