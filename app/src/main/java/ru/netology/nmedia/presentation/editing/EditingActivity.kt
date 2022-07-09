package ru.netology.nmedia.presentation.editing

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R

class EditingActivity : AppCompatActivity() {
    private lateinit var btnEdit: Button
    private lateinit var etEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editing)

        btnEdit = findViewById(R.id.edit_btn)
        etEdit = findViewById(R.id.edit_et)

        val text = intent?.getStringExtra(Intent.EXTRA_TEXT)
        text?.let {
            if (text.isNotBlank()) {
                etEdit.setText(text)
            }
        }

        btnEdit.setOnClickListener {
            val intent = Intent()
            if (etEdit.text.isNotBlank()) {
                val content = etEdit.text.toString()
                intent.apply {
                    putExtra(Intent.EXTRA_TEXT, content)
                    setResult(Activity.RESULT_OK, intent)
                }
            } else {
                intent.apply {
                    setResult(Activity.RESULT_CANCELED, intent)
                }
                Snackbar.make(
                    this@EditingActivity,
                    btnEdit,
                    getString(R.string.empty_content),
                    Snackbar.LENGTH_INDEFINITE
                ).show()
            }
            finish()
        }
    }
}