package ru.netology.nmedia.presentation.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.presentation.creating.CreateActivity

class NewPostResultContract : ActivityResultContract<Unit, String?>() {
    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == Activity.RESULT_OK) {
            intent?.getStringExtra(Intent.EXTRA_TEXT)
        } else {
            null
        }

    override fun createIntent(context: Context, input: Unit): Intent =
        Intent(context, CreateActivity::class.java)
}