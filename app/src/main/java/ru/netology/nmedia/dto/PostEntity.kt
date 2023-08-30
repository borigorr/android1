package ru.netology.nmedia.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val author: String,
    val content: String,
    val published: String,
    val likeCount: Int = 0,
    val linkToVideo: String? = null,
    val shareCount: Int = 0,
    val viewCount: Int = 0,
    val likeByMe: Boolean = false,
) {
    fun toDto() = Post(
        id = id,
        author = author,
        content = content,
        published = published,
        likeCount = likeCount,
        linkToVideo = linkToVideo,
        shareCount = shareCount,
        viewCount = viewCount,
        likeByMe = likeByMe
    )

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(
                id = dto.id,
                author = dto.author,
                content = dto.content,
                published = dto.published,
                likeCount = dto.likeCount,
                linkToVideo = dto.linkToVideo,
                shareCount = dto.shareCount,
                viewCount = dto.viewCount,
                likeByMe = dto.likeByMe
            )
    }
}
