package com.sitapuramargram.a7minuteworkout.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sitapuramargram.a7minuteworkout.R
import com.sitapuramargram.a7minuteworkout.databinding.ActivityExerciseBinding
import com.sitapuramargram.a7minuteworkout.databinding.ActivityFinishBinding
import com.sitapuramargram.a7minuteworkout.db.SqlliteOpenHelper
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    lateinit var finishBinding: ActivityFinishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finishBinding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(finishBinding.root)

        setSupportActionBar(finishBinding.toolbar)
        val actionBar = supportActionBar
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true)

        }
        finishBinding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
        finishBinding.btnFinish.setOnClickListener {
            finish()
        }
        addDateToDatabase()
    }

    private fun addDateToDatabase(){

        val calender = Calendar.getInstance()
        val dateTime = calender.time
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss",Locale.getDefault())
        val date = sdf.format(dateTime)
        val dbHandler = SqlliteOpenHelper(this,null)
        dbHandler.addDate(date)


    }
}