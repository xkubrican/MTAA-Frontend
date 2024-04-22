package com.example.yourslovakiafrontend.view_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yourslovakiafrontend.R
import fiit.mtaa.yourslovakia.models.PointOfInterest

class RecyclerViewAdapter(private val dataList: List<PointOfInterest>, private val context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_custom_object)
        val nameTextView: TextView = itemView.findViewById(R.id.text_custom_object_name)
        val descriptionTextView: TextView = itemView.findViewById(R.id.text_custom_object_description)
        val detailsButton: Button = itemView.findViewById(R.id.button_go_to_details)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.poi_object, parent, false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = dataList[position]
        //holder.imageView.setImageResource(item.imageResId)
        holder.nameTextView.text = item.name
        //holder.descriptionTextView.text = item.description
        holder.detailsButton.setOnClickListener {
            // Handle button click to
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
