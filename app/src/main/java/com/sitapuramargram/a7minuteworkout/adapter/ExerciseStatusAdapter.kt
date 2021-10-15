package com.sitapuramargram.a7minuteworkout.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sitapuramargram.a7minuteworkout.R
import com.sitapuramargram.a7minuteworkout.databinding.ItemExerciseStatusBinding
import com.sitapuramargram.a7minuteworkout.model.ExerciseModel

class ExerciseStatusAdapter(val items:ArrayList<ExerciseModel>, val context: Context) : RecyclerView.Adapter<ExerciseStatusAdapter.MyViewHolder>() {




    inner class MyViewHolder(itemBinding: ItemExerciseStatusBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        val tvItem = itemBinding.itemText

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding = ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model: ExerciseModel = items[position]
        holder.tvItem.text = model.id.toString()
        if(model.isSelected) {
            holder.tvItem.background =
                ContextCompat.getDrawable(context, R.drawable.item_cicular_thin_color_accent_border)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
        }
        else if (model.isCompleted){

            holder.tvItem.background =
                ContextCompat.getDrawable(context, R.drawable.item_circular_color_accent_background)
            holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
        }else{
            holder.tvItem.background =
                ContextCompat.getDrawable(context, R.drawable.item_circular_color)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
        }


    }

    override fun getItemCount(): Int {
        return  items.size
    }
}