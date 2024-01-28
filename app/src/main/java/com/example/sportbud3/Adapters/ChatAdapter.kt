package com.example.sportbud3.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportbud3.Model.Chat
import com.example.sportbud3.R
import kotlinx.android.synthetic.main.chat_layouyt_git.view.*

class ChatAdapter(private val chatList: MutableList<Chat>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    private var currentUserID: String = ""

    private var myString: String = ""

    fun setMyString(string: String) {
        myString = string
    }

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //bunlar duz beyaxz olan view
        /*
        private val nicknameTextView: TextView = itemView.findViewById(R.id.nicknameTextView)
      //  private val profileImageView: ImageView = itemView.findViewById(R.id.profileImageView)
        private val chatTextView: TextView = itemView.findViewById(R.id.chatTextView)
*/

        private val messageText: TextView = itemView.findViewById(R.id.tv_message)
        private val nickname: TextView = itemView.findViewById(R.id.nicknameTextView)
        fun bind(chat: Chat) {
            // nicknameTextView.text = chat.nickname
            // Set the profile image here using Glide or Picasso library
            messageText.text = chat.text
            nickname.text = chat.nickname
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_layouyt_git, parent, false)
        return ChatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val currentMessage = chatList[position]


        if (currentMessage.profile_id == myString) {
            holder.itemView.layout_sender.visibility = View.VISIBLE
            holder.itemView.OtherUserView.visibility = View.GONE
            holder.itemView.tv_message.apply {
                visibility = View.VISIBLE

                holder.itemView.tv_message.text = currentMessage.text
            }
        }
        if(currentMessage.profile_id != myString) {
            holder.itemView.OtherUserView.visibility = View.VISIBLE

            holder.itemView.tv_bot_message.apply {
                holder.itemView.tv_bot_message.text = currentMessage.text
                holder.itemView.layout_sender.visibility = View.GONE
                holder.itemView.nicknameOtherUser.text = currentMessage.nickname
            }

        }

        holder.bind(currentMessage)


    }


    override fun getItemCount() = chatList.size

    fun addChatMessage(chatMessage: Chat) {
        chatList.add(chatMessage)
        notifyItemInserted(chatList.size - 1)
    }
}