package ru.netology.nmedia

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import org.w3c.dom.Text
import ru.netology.nmedia.utils.changeCountString

class MainActivity : AppCompatActivity() {

    lateinit var btnLike: ImageButton
    lateinit var btnShare: ImageButton

    lateinit var tvLikes: TextView
    lateinit var tvShares: TextView

    var likeEnabled: Boolean = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLike = findViewById(R.id.btn_like)
        btnShare = findViewById(R.id.btn_share)

        tvShares = findViewById(R.id.tv_share)
        tvLikes = findViewById(R.id.tv_likes)

        btnLike.setOnClickListener {
            if(!likeEnabled)
            {
                tvLikes.text = changeCountString(tvLikes.text.toString().toInt(), 1)
                likeEnabled = true
            }
            else{
                tvLikes.text = changeCountString(tvLikes.text.toString().toInt(), -1)
                likeEnabled = false
            }
        }

        btnShare.setOnClickListener {
            tvShares.text = changeCountString(tvShares.text.toString().toInt(), 1)
        }
    }
}