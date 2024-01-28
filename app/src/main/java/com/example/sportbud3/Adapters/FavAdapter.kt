package com.example.sportbud3.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportbud3.Constants
import com.example.sportbud3.R

class FavAdapter(private var myFavList: List<String>) :
    RecyclerView.Adapter<FavAdapter.UserViewHolder>() {


    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView:ImageView  = itemView.findViewById(R.id.imageviewFav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.room_lits_item_welcome, parent, false)
        myFavList = Constants.GlobalData.fav_arrayList
        return UserViewHolder(view)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        when (myFavList.get(position)) {
            "638e6a894321c700312ff67e" -> holder.imageView.setBackgroundResource(R.drawable.yoga)
            "638e69cd4321c700312ff67b" -> holder.imageView.setBackgroundResource(R.drawable.plates)
            "638e675d4321c700312ff674" -> holder.imageView.setBackgroundResource(R.drawable.cardio)
            "638e69544321c700312ff679" -> holder.imageView.setBackgroundResource(R.drawable.running)
            else -> holder.imageView.setBackgroundResource(0)
        }
    }


    override fun getItemCount(): Int {
        return myFavList.size
    }


}