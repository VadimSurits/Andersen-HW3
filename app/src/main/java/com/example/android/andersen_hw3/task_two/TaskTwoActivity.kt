package com.example.android.andersen_hw3.task_two

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
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
//                tryLoadPictureWithGlide(url)
                tryLoadPictureWithAsyncTask(url)
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun tryLoadPictureWithGlide(url: String?) {
        Glide.with(this)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?, model: Any?,
                    target: Target<Drawable>?, isFirstResource: Boolean
                ): Boolean {
                    showToast()
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?, model: Any?, target: Target<Drawable>?,
                    dataSource: DataSource?, isFirstResource: Boolean
                ): Boolean {
                    Log.d(logTag, getString(R.string.on_resource_ready_log_message))
                    return false
                }
            })
            .into(mImageView)
    }

    private fun tryLoadPictureWithAsyncTask(url: String?) {
        val myTask = AdditionalTask(mImageView)
        myTask.execute(url)
    }

    private inner class AdditionalTask(var imageView: AppCompatImageView) :
        AsyncTask<String, Void, Bitmap?>() {

        override fun doInBackground(vararg urls: String?): Bitmap? {
            val imageUrl = urls[0]
            var image: Bitmap? = null;
            try {
                val incoming = java.net.URL(imageUrl).openStream()
                image = BitmapFactory.decodeStream(incoming)
            } catch (e: Exception) {
                Log.e(logTag, getString(R.string.asynс_task_download_error))
                e.printStackTrace()
                runOnUiThread {
                    showToast()
                }
            }
            return image
        }

        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }

    private fun showToast() {
        Toast.makeText(
            this@TaskTwoActivity,
            getString(R.string.load_failed_message),
            Toast.LENGTH_LONG
        ).show()
    }
}
