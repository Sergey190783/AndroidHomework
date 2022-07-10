package ru.netology.nmedia.presentation.editing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.presentation.feed.FeedFragment.Companion.textArg
import ru.netology.nmedia.presentation.PostViewModel
import ru.netology.nmedia.utils.AndroidUtils

class EditPostFragment : Fragment() {
    private lateinit var btnEdit: Button
    private lateinit var etEdit: EditText
    private val viewModel: PostViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_post, container, false)

        btnEdit = view.findViewById(R.id.edit_btn)
        etEdit = view.findViewById(R.id.edit_et)

        arguments?.textArg
            ?.let {
                etEdit.setText(it)
                etEdit.requestFocus()
            }

        btnEdit.setOnClickListener {
            AndroidUtils.hideKeyboard(requireView())
            if (etEdit.text.isNotBlank()) {
                viewModel.changeContent(etEdit.text.toString())
                viewModel.save()
                findNavController().navigate(R.id.action_editPostFragment_to_feedFragment)
            } else {
                Snackbar.make(
                    requireContext(),
                    requireView(),
                    getString(R.string.empty_content),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        return view
    }
}