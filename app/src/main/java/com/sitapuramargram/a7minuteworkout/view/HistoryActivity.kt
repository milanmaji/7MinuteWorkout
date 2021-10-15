package com.sitapuramargram.a7minuteworkout.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sitapuramargram.a7minuteworkout.R
import com.sitapuramargram.a7minuteworkout.adapter.HistoryAdapter
import com.sitapuramargram.a7minuteworkout.databinding.ActivityHistoryBinding
import com.sitapuramargram.a7minuteworkout.db.SqlliteOpenHelper

class HistoryActivity : AppCompatActivity() {

    lateinit var historyBinding: ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyBinding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(historyBinding.root)
        setSupportActionBar(historyBinding.toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "HISTORY"

        }

        historyBinding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
        getAllCompletedDates()

    }

    private fun  getAllCompletedDates(){

        val dbHandler = SqlliteOpenHelper(this,null)
        val allCompletedDateList = dbHandler.getAllCompletedDatesList()

        if (allCompletedDateList.size > 0) {
            // Here if the List size is greater then 0 we will display the item in the recycler view or else we will show the text view that no data is available.
            historyBinding.tvHistory.visibility = View.VISIBLE
            historyBinding.rvHistory.visibility = View.VISIBLE
            historyBinding.tvNoDataAvailable.visibility = View.GONE

            // Creates a vertical Layout Manager
            historyBinding.rvHistory.layoutManager = LinearLayoutManager(this)

            // History adapter is initialized and the list is passed in the param.
            val historyAdapter = HistoryAdapter(this, allCompletedDateList)

            // Access the RecyclerView Adapter and load the data into it
            historyBinding.rvHistory.adapter = historyAdapter
        } else {
            historyBinding.tvHistory.visibility = View.GONE
            historyBinding.rvHistory.visibility = View.GONE
            historyBinding.tvNoDataAvailable.visibility = View.VISIBLE
        }

    }


}