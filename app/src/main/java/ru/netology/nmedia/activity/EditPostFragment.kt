package ru.netology.nmedia.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.repository.PostViewModelRepository
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.runBlocking
import ru.netology.nmedia.activity.MainActivity.Companion.editId
import ru.netology.nmedia.activity.MainActivity.Companion.editLink
import ru.netology.nmedia.activity.MainActivity.Companion.editText
import ru.netology.nmedia.repository.PostDraftRepository
import ru.netology.nmedia.repository.PostDraftRepositoryPreferenceImpl

class EditPostFragment : Fragment() {

    private lateinit var binding: FragmentNewPostBinding

    private val viewModel: PostViewModelRepository by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private lateinit var draftRepo: PostDraftRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        draftRepo = PostDraftRepositoryPreferenceImpl(requireContext())
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            binding.apply {
                runBlocking {
                    draftRepo.setText(edit.text.toString())
                    draftRepo.setLink(link.text.toString())
                }
                findNavController().navigateUp()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewPostBinding.inflate(inflater)

        var EditText = arguments?.editText ?: ""
        var EditLink = arguments?.editLink ?: ""
        val id = arguments?.editId ?: 0
        val isNewPost = id == 0
        if (isNewPost) {
            runBlocking {
                EditText = draftRepo.getText()
                EditLink = draftRepo.getLink()
            }
        }
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