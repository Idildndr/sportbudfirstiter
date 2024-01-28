package com.example.sportbud3.Adapters

import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.sportbud.Model.Clientsİnfo
import com.example.sportbud.Model.RoomListItem
import com.example.sportbud3.Activities.RoomDetailsActivity
import com.example.sportbud3.Constants
import com.example.sportbud3.R
import com.example.sportbud3.databinding.RoomlistItemBinding
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext


class RoomListItemAdapter : RecyclerView.Adapter<RoomListItemAdapter.RoomListViewHolder>() {

    private var userId = Constants.GlobalData.userID
    private var adminId = Constants.GlobalData.admin_id

    inner class RoomListViewHolder(val binding: com.example.sportbud3.databinding.RoomlistItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<RoomListItem>() {


        override fun areItemsTheSame(oldItem: RoomListItem, newItem: RoomListItem): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: RoomListItem, newItem: RoomListItem): Boolean {
            return oldItem._id == newItem._id
        }

    }
    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomListViewHolder {

        return RoomListViewHolder(
            RoomlistItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RoomListViewHolder, position: Int) {
        val currentRoom = differ.currentList[position]


        // Constants.roomlistArraylist = differ.currentList
        holder.binding.apply {

            if (userId == differ.currentList[position].contents?.admin_id) {
                Log.d("apater_user", userId)
                differ.currentList[position].contents?.admin_id?.let { Log.d("adapter_admin", it) }
                holder.itemView.invalidate()


            } else {
                holder.itemView.invalidate()

            }

            when (currentRoom.contents?.category_id) {
                "639f73052b33750031d145f9" -> imageview.setBackgroundResource(R.drawable.yoga)

                "638e69cd4321c700312ff67b" -> imageview.setBackgroundResource(R.drawable.plates)

                "638e675d4321c700312ff674" -> imageview.setBackgroundResource(R.drawable.cardio)

                "638e69544321c700312ff679" -> imageview.setBackgroundResource(R.drawable.running)


                else -> { // Note the block

                }


            }




            holder.itemView.setOnClickListener {
                val intent = Intent(it.context, RoomDetailsActivity::class.java)

                intent.putExtra("newtitle", currentRoom.title)

                intent.putExtra("newloc", currentRoom.contents?.location)

                intent.putExtra("newdate", currentRoom.date)

                intent.putExtra("newimage", currentRoom.contents?.category_id)

                intent.putExtra("newdes", currentRoom.contents?.description)


                intent.putExtra("newslogan", currentRoom.contents?.slogan)


                intent.putExtra("capacity", currentRoom.contents?.capacity.toString())

                intent.putExtra("activity_id", currentRoom._id)
                Constants.GlobalData.admin_id = currentRoom.contents?.admin_id.toString()
                Constants.GlobalData.admin_id = currentRoom._id.toString()
                intent.putExtra("short_desc", currentRoom.contents?.short_description)


                val clientsInfoList = currentRoom.contents?.clients_infos
                val userNameList =
                    clientsInfoList?.map { it.user_name } // extract user_name values from clientsInfoList
                intent.putStringArrayListExtra("userNames", ArrayList(userNameList))

                val usern_id_list = clientsInfoList?.map { it.user_id }
                intent.putStringArrayListExtra("userIds", ArrayList(usern_id_list))

                it.context.startActivity(intent)

            }

            RoomName.text = currentRoom.title
            val formattedTime = SimpleDateFormat("HH:mm:ss", Locale.US).format(currentRoom.date)

            RoomDateTextView.text = "$formattedTime\n${
                SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.US
                ).format(currentRoom.contents?.startedActRoom)
            }"
            RoomLocationTextView.text = currentRoom.contents?.location
        }

    }

    fun hideItem(position: Int) {
        notifyItemRemoved(position)
    }

    override fun getItemCount() = differ.currentList.size


}
