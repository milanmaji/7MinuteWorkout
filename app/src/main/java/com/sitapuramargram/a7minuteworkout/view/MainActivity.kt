package com.sitapuramargram.a7minuteworkout.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sitapuramargram.a7minuteworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mainActivityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityMainBinding.root)



        mainActivityMainBinding.llStart.setOnClickListener {

            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }
        mainActivityMainBinding.llBMI.setOnClickListener {
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }

        mainActivityMainBinding.llHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }


    }
}