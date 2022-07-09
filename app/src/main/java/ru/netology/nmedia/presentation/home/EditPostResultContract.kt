package ru.netology.nmedia.presentation.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.presentation.creating.CreateActivity
import ru.netology.nmedia.presentation.editing.EditingActivity

class EditPostResultContract() : ActivityResultContract<Post, String?>() {
    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == Activity.RESULT_OK) {
            intent?.getStringExtra(Intent.EXTRA_TEXT)
        } else {
            null
        }

    override fun createIntent(context: Context, input: Post): Intent =
        Intent(context, EditingActivity::class.java).apply {
            putExtra(Intent.EXTRA_TEXT, input.content)
        }
}