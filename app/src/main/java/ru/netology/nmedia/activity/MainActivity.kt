package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.helpers.IntArg
import ru.netology.nmedia.helpers.StringArg

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text?.isNotBlank() != true) {
                return@let
            }
            intent.removeExtra(Intent.EXTRA_TEXT)
            findNavController(this, R.id.fragment_container).navigate(
                R.id.action_feedFragment_to_editPostFragment,
                Bundle().apply {
                    editText = text
                }
            )
        }
    }

    companion object {
        var Bundle.editText: String? by StringArg
        var Bundle.editLink: String? by StringArg
        var Bundle.editId: Int? by IntArg
    }
}