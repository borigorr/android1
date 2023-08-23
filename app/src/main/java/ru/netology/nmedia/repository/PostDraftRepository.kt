package ru.netology.nmedia.repository

import kotlinx.coroutines.flow.Flow

interface PostDraftRepository {
    suspend fun setText(text: String)
    suspend fun getText(): String
    suspend fun getLink(): String
    suspend fun setLink(text: String)
}