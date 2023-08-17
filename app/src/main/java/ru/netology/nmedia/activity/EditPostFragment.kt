package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.repository.PostViewModelRepository
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.activity.MainActivity.Companion.editId
import ru.netology.nmedia.activity.MainActivity.Companion.editLink
import ru.netology.nmedia.activity.MainActivity.Companion.editText

class EditPostFragment : Fragment() {

    private lateinit var binding: FragmentNewPostBinding

    private val viewModel: PostViewModelRepository by viewModels(
        ownerProducer = ::requireParentFragment
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewPostBinding.inflate(inflater)

        val id = arguments?.editId ?: 0
        val EditText = arguments?.editText ?: ""
        val EditLink = arguments?.editLink ?: ""

        binding.apply {
            edit.setText(EditText)
            link.setText(EditLink)
            ok.setOnClickListener {
                val post = viewModel.getByIdOrEmpty(id).copy(
                    content = edit.text.toString(),
                    linkToVideo = link.text.toString()
                )
                viewModel.edit(post)
                viewModel.save()
                findNavController().navigateUp()
            }
        }

        return binding.root
    }
}