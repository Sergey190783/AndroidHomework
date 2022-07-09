package ru.netology.nmedia.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    private val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
        if (result == null) {
            Snackbar.make(
                this@MainActivity,
                recyclerView,
                getString(R.string.empty_content),
                Snackbar.LENGTH_SHORT
            ).show()
            return@registerForActivityResult
        }
        mainViewModel.changeContent(result)
        mainViewModel.save()
    }

    val editPostLauncher = registerForActivityResult(EditPostResultContract()) { result ->
        if (result == null) {
            Snackbar.make(
                this@MainActivity,
                recyclerView,
                getString(R.string.empty_content),
                Snackbar.LENGTH_SHORT
            ).show()
            return@registerForActivityResult
        }
        mainViewModel.changeContent(result)
        mainViewModel.save()
    }

    private val mainViewModel: MainActivityViewModel by viewModels()
    private val adapter = PostsAdapter(object : OnInteractionListener {
        override fun onLike(id: Long) {
            mainViewModel.like(id)
        }

        override fun onEdit(post: Post) {
            mainViewModel.edit(post)
            editPostLauncher.launch(post)
        }

        override fun onRemove(id: Long) {
            mainViewModel.remove(id)
        }

        override fun onShare(post: Post) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.content)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        override fun onClickVideoPreview(url: String) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
    )

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler)
        fab = findViewById(R.id.fab)

        recyclerView.adapter = adapter

        mainViewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        fab.setOnClickListener {
            newPostLauncher.launch()
        }
    }
}