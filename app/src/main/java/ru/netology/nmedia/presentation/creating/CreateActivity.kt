package ru.netology.nmedia.presentation.creating

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R

class CreateActivity : AppCompatActivity() {

    private lateinit var btnCreate: Button
    private lateinit var etCreate: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        btnCreate = findViewById(R.id.create_btn)
        etCreate = findViewById(R.id.create_et)
        etCreate.requestFocus()

        btnCreate.setOnClickListener {
            val intent = Intent()
            if(etCreate.text.isBlank()){
                setResult(Activity.RESULT_CANCELED,intent)
            }else{
                val content = etCreate.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }
}