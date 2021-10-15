package com.sitapuramargram.a7minuteworkout.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sitapuramargram.a7minuteworkout.databinding.ItemHistoryRowBinding

class HistoryAdapter(val context: Context, val items: ArrayList<String>):
RecyclerView.Adapter<HistoryAdapter.ViewHolder>()
{



    inner class ViewHolder(itemBinding: ItemHistoryRowBinding): RecyclerView.ViewHolder(itemBinding.root){
        val llHistoryItemMain = itemBinding.llHistoryItemMain
        val tvItem = itemBinding.tvItem
        val tvPosition = itemBinding.tvPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val itemBinding = ItemHistoryRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date: String = items.get(position)

        holder.tvPosition.text = (position + 1).toString()
        holder.tvItem.text = date

        // Updating the background color according to the odd/even positions in list.
        if (position % 2 == 0) {
            holder.llHistoryItemMain.setBackgroundColor(
                Color.parseColor("#EBEBEB")
            )
        } else {
            holder.llHistoryItemMain.setBackgroundColor(
                Color.parseColor("#FFFFFF")
            )
        }
    }

    override fun getItemCount(): Int {
       return items.size
    }


}