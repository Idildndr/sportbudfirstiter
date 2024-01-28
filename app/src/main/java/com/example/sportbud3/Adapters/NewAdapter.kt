package com.example.sportbud3.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportbud.Model.RoomListItem
import com.example.sportbud3.R
import kotlinx.android.synthetic.main.roomlist_item.view.*

class NewAdapter (private val dataList: ArrayList<RoomListItem>) : RecyclerView.Adapter<NewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.roomlist_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        item.title = dataList[position].title
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: RoomListItem) {
            itemView.RoomName.text = item.title
        }
    }
}