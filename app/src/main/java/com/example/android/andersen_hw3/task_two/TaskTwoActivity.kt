package com.example.android.andersen_hw3.task_two

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.android.andersen_hw3.R

class TaskTwoActivity : AppCompatActivity() {

    private val logTag = TaskTwoActivity::class.java.simpleName

    private lateinit var mEditText: AppCompatEditText
    private lateinit var mImageView: AppCompatImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_two)
        mEditText = findViewById(R.id.et)
        mImageView = findViewById(R.id.image_view)
        mEditText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val url = v.text.toString()
                tryLoadPicture(url)
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun tryLoadPicture(url: String?) {
        Glide.with(this)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?, model: Any?,
                    target: Target<Drawable>?, isFirstResource: Boolean
                ): Boolean {
                    Toast.makeText(
                        this@TaskTwoActivity,
                        getString(R.string.load_failed_message),
                        Toast.LENGTH_LONG
                    ).show()
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?, model: Any?, target: Target<Drawable>?,
                    dataSource: DataSource?, isFirstResource: Boolean
                ): Boolean {
                    Log.d(logTag, "OnResourceReady")
                    return false
                }
            })
            .into(mImageView)
    }
}
