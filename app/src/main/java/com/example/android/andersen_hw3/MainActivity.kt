package com.example.android.andersen_hw3

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.android.andersen_hw3.task_one.TaskOneActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun launchTaskOneActivity(view: View) {
        val intentTaskOne = Intent(this, TaskOneActivity::class.java)
        startActivity(intentTaskOne)
    }

    fun launchTaskTwoActivity(view: View) {

    }
}