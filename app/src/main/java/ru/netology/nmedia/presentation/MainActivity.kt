package ru.netology.nmedia.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import ru.netology.nmedia.R

class MainActivity : AppCompatActivity() {
    private lateinit var btnLike: ImageButton
    private lateinit var btnShare: ImageButton

    private lateinit var tvLikes: TextView
    private lateinit var tvShares: TextView
    private lateinit var tvGreeting: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var tvViews: TextView

    private val mainViewModel: MainActivityViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLike = findViewById(R.id.btn_like)
        btnShare = findViewById(R.id.btn_share)

        tvShares = findViewById(R.id.tv_share)
        tvLikes = findViewById(R.id.tv_likes)
        tvGreeting = findViewById(R.id.tv_greeting)
        tvDate = findViewById(R.id.tv_date)
        tvViews = findViewById(R.id.tv_views)
        tvAuthor = findViewById(R.id.tv_author)

        mainViewModel.data.observe(this) { post ->
            tvGreeting.text = post.content
            tvDate.text = post.published
            tvAuthor.text = post.author

            tvLikes.text = post.likes.toString()
            tvShares.text = post.reposts.toString()
            tvViews.text = post.views.toString()
        }

        btnLike.setOnClickListener {
            mainViewModel.like()
        }

        btnShare.setOnClickListener {
            mainViewModel.share()
        }
    }
}