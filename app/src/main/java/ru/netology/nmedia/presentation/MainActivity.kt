package ru.netology.nmedia.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.utils.PostDiffCallback

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    private val mainViewModel: MainActivityViewModel by viewModels()
    private val adapter = PostAdapter(
        { mainViewModel.like(it) },
        { mainViewModel.share(it) }
    )

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler)
        recyclerView.adapter = adapter

        mainViewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
    }
}