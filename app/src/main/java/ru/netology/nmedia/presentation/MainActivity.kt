package ru.netology.nmedia.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.utils.AndroidUtils

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAddPost: ImageButton
    private lateinit var etPost: EditText
    private lateinit var editGroup: Group
    private lateinit var editedPostTv: TextView
    private lateinit var btnCancelEditing: ImageButton

    private val mainViewModel: MainActivityViewModel by viewModels()
    private val adapter = PostsAdapter(object : OnInteractionListener {
        override fun onLike(id: Long) {
            mainViewModel.like(id)
        }

        override fun onEdit(post: Post) {
            editGroup.visibility = View.VISIBLE
            mainViewModel.edit(post)
            editedPostTv.text = post.content
        }

        override fun onRemove(id: Long) {
            mainViewModel.remove(id)
        }

        override fun onShare(id: Long) {
            mainViewModel.share(id)
        }
    }
    )

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler)
        btnAddPost = findViewById(R.id.btn_add_post)
        etPost = findViewById(R.id.content)
        editGroup = findViewById(R.id.edit_group)
        editedPostTv = findViewById(R.id.tv_edit_message)
        btnCancelEditing = findViewById(R.id.btn_cancel_edit)

        recyclerView.adapter = adapter

        mainViewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        mainViewModel.edited.observe(this) { post ->
            if (post.id == 0L)
                return@observe

            etPost.apply {
                requestFocus()
                setText(post.content)
            }
        }

        btnAddPost.setOnClickListener {
            if (etPost.text.isNullOrBlank()) {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.empty_content),
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            mainViewModel.changeContent(etPost.text.toString())
            mainViewModel.save()

            etPost.setText("")
            etPost.clearFocus()
            AndroidUtils.hideKeyboard(etPost)
            editGroup.visibility = View.GONE
        }

        btnCancelEditing.setOnClickListener {
            editGroup.visibility = View.GONE
            etPost.setText("")
            etPost.clearFocus()
            AndroidUtils.hideKeyboard(etPost)
        }
    }
}