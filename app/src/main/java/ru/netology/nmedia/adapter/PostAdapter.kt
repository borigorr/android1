package ru.netology.nmedia.adapter

import android.media.browse.MediaBrowser.ItemCallback
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.helpers.NumberHelper

typealias OnClickListener = (post: Post) -> Unit

class PostAdapter(val onClickLikeListener: OnClickListener, val onClickShareListener: OnClickListener): ListAdapter<Post, PostViewHolder>(PostDiffUntil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onClickLikeListener, onClickShareListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = this.currentList[position]
        holder.bind(post)
    }

}

class PostViewHolder(

    private val binding: CardPostBinding,
    val onClickLikeListener: OnClickListener,
    val onClickShareListener: OnClickListener
): ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            viewCount.text = NumberHelper.intToShortString(post.viewCount)
            sharedCount.text = NumberHelper.intToShortString(post.shareCount)
            likesCount.text = NumberHelper.intToShortString(post.likeCount)
            likes.setOnClickListener() {
                onClickLikeListener(post)
            }
            sharedIcon.setOnClickListener() {
                onClickShareListener(post)
            }
        }
    }
}

class PostDiffUntil(): DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.equals(newItem)
    }

}