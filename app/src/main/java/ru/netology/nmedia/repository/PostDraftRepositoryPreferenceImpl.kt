package ru.netology.nmedia.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.netology.nmedia.activity.MainActivity.Companion.dataStore

class PostDraftRepositoryPreferenceImpl(private val context: Context): PostDraftRepository {

    private val LINK_STORAGE_KEY = stringPreferencesKey("link_storage_key")
    private val TEXT_STORAGE_KEY = stringPreferencesKey("text_storage_key")


     override suspend fun setText(text: String) {
        context.dataStore.edit {
            it[TEXT_STORAGE_KEY] = text
        }
    }

    override suspend fun getText(): String {
        return context.dataStore.data.first()[TEXT_STORAGE_KEY] ?: ""
    }

    override suspend fun getLink(): String {
        return context.dataStore.data.first()[LINK_STORAGE_KEY] ?: ""
    }

    override suspend fun setLink(link: String) {
        context.dataStore.edit {
            it[LINK_STORAGE_KEY] = link
        }
    }
}