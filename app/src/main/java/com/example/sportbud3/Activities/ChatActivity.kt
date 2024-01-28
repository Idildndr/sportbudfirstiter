package com.example.sportbud3.Activities

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Response

import com.example.sportbud3.Adapters.ChatAdapter
import com.example.sportbud3.Constants
import com.example.sportbud3.Constants.BASE_URL
import com.example.sportbud3.Constants.END_POINT_CHAT
import com.example.sportbud3.Model.Chat
import com.example.sportbud3.Model.ChatPost
import com.example.sportbud3.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.TimeUnit

internal class ChatActivity : AppCompatActivity() {


    private lateinit var chatAdapter: ChatAdapter
    private lateinit var activityId: String
    private lateinit var profile_id: String
    private lateinit var nickname: String
    private lateinit var userNameList: ArrayList<String>
    private val chatPosts = mutableListOf<ChatPost>()
    val chatMessages = mutableListOf<Chat>()

//    val activity_Id_demo = intent.getStringExtra("activity_id")
    //  val user_Id_demo = intent.getStringExtra("user_id")


    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        profile_id = Constants.GlobalData.userID
        nickname = Constants.GlobalData.userNickname
        nickname = Constants.GlobalData.userNickname

        activityId = intent.getStringExtra("activityID").toString()

        val bundle = Bundle()
        bundle.putString("data", "63979dc3e86bed0031cecaf5")


        GetActivityChatsTask().execute(activityId)

        userNameList = intent.getStringArrayListExtra("userNameList") as ArrayList<String>

      //  ll_layout_bar.isVisible = userNameList.contains(nickname)

        if (!userNameList.contains(nickname)) {
         ll_layout_bar.isVisible = false
        }else{
            ll_layout_bar.isVisible = true

        }

        btn_send.setOnClickListener {
            val message = edit_text_message.text.toString()
            if (message.isNotEmpty()) {
                GlobalScope.launch(Dispatchers.IO) {

                    sendChatPostRequest(activityId, nickname, profile_id, message)

                }
                edit_text_message.text.clear()
            }
        }


    }

    private fun parseChatMessages(responseBody: String): List<Chat> {
        val jsonArray = JSONArray(responseBody)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val id = jsonObject.getString("_id")
            val activity_id = jsonObject.getString("activity_id")
            val nickname = jsonObject.getString("nickname")
            val profile_id = jsonObject.getString("profile_id")
            val text = jsonObject.getString("text")
            val chatMessage = Chat(id, activity_id, nickname, profile_id, text)
            val chatPostMessage = ChatPost(id, nickname, profile_id, text)


            chatMessages.add(chatMessage)
            chatPosts.add(chatPostMessage)

        }

        return chatMessages
    }


    @SuppressLint("StaticFieldLeak")
    private inner class GetActivityChatsTask : AsyncTask<String, Void, List<Chat>>() {
        override fun doInBackground(vararg params: String?): List<Chat> {
            val id = params[0]
            val urlString = "$BASE_URL$END_POINT_CHAT?id=$id"
            val request = Request.Builder()
                .url(urlString)
                .build()

            val client = OkHttpClient()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val responseString = response.body?.string()
                return parseChatMessages(responseString!!)
            }
        }


        override fun onPostExecute(result: List<Chat>) {
            super.onPostExecute(result)
            chatAdapter = ChatAdapter(result as MutableList<Chat>)
            chatAdapter.setMyString(profile_id)
            recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@ChatActivity)
                val bundle = Bundle()
                recyclerView.scrollToPosition(chatAdapter.itemCount - 1)

                bundle.putString("rbdgfx", "dfg nbcgv")
                adapter = chatAdapter
            }
        }
    }

    fun sendChatPostRequest(activityId: String, nickname: String, profileId: String, text: String) {
        val queue = Volley.newRequestQueue(this@ChatActivity)
        val urlString = "$BASE_URL$END_POINT_CHAT?id=$activityId"

        // Create a new ChatPost object with the required data
        val chatPost = ChatPost(activityId, nickname, profileId, text)

        // Convert the ChatPost object to JSON using Gson library
        val jsonRequestBody = Gson().toJson(chatPost)

        // Create a new JsonObjectRequest
        val jsonObjectRequest = @SuppressLint("NotifyDataSetChanged")
        object : JsonObjectRequest(Method.POST, urlString, null,
            Response.Listener { response ->
                // Handle the server response
                val id = response.getString("_id")
                val chatPost = ChatPost(activityId, nickname, profileId, text)
                val chat = Chat("", activityId, nickname, profileId, text)
                chatPost.activity_id = id // set the id to the response ihgmnfyjgmd
                chatMessages.add(chat)
                chatAdapter.notifyDataSetChanged() // notify the adapter of the new message
                recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
                Log.d("sendChatPostRequest", "Chat post sent successfully with ID $id")
            },
            Response.ErrorListener { error ->
                // Handle errors that occur while making the request
                Log.e("sendChatPostRequest", "Error sending chat post: $error")
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return jsonRequestBody.toByteArray(Charset.forName("utf-8"))
            }

        }

        queue.add(jsonObjectRequest)
    }


}
