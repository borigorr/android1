package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostEdit

const val POST_ID = "POST_ID"
const val POST_CONTENT = "POST_CONTENT"
const val POST_URL = "POST_URL"

class EditPostResultContract : ActivityResultContract<PostEdit, PostEdit?>() {

    override fun createIntent(context: Context, input: PostEdit): Intent {
        return Intent(context, EditPostActivity::class.java)
            .putExtra(POST_ID, input.id)
            .putExtra(POST_CONTENT, input.content)
        //   .putExtra(POST_CONTENT, input.content)
    }


    override fun parseResult(resultCode: Int, intent: Intent?): PostEdit? {
        return if (resultCode == Activity.RESULT_OK) {
            val id = intent?.getIntExtra(POST_ID, 0) ?: 0
            val content = intent?.getStringExtra(POST_CONTENT)
            PostEdit(id = id, content = content)
        } else {
            null
        }
    }
}