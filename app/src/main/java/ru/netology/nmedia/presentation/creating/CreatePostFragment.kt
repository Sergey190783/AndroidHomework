package ru.netology.nmedia.presentation.creating

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.presentation.PostViewModel
import ru.netology.nmedia.utils.AndroidUtils

class CreatePostFragment : Fragment() {
    private lateinit var btnCreate: Button
    private lateinit var etCreate: EditText
    private val viewModel: PostViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_post, container, false)
        btnCreate = view.findViewById(R.id.create_btn)
        etCreate = view.findViewById(R.id.create_et)
        etCreate.requestFocus()

        btnCreate.setOnClickListener {
            viewModel.changeContent(etCreate.text.toString())
            viewModel.save()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }
        return view
    }
}