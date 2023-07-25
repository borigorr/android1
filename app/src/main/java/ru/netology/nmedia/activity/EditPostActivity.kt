package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class EditPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewPostBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewPostBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val content = intent.getStringExtra(POST_CONTENT)
        val id = intent.getIntExtra(POST_ID, 0)

        with(binding) {
            edit.setText(content)
            ok.setOnClickListener {
                val intent = Intent()
                if (binding.edit.text.isNullOrBlank()) {
                    setResult(Activity.RESULT_CANCELED, intent)
                } else {
                    intent.putExtra(POST_CONTENT, edit.text.toString())
                        .putExtra(POST_ID, id)
                    setResult(Activity.RESULT_OK, intent)
                }
                finish()
            }
        }
    }
}