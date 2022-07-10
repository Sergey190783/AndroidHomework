package ru.netology.nmedia.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.R

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
    }
}