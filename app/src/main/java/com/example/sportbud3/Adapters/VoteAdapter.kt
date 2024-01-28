package com.example.sportbud3.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.sportbud3.Constants
import com.example.sportbud3.Model.LoyaltyData
import com.example.sportbud3.R
import com.example.sportbud3.services.MyApiService
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_room_details.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VoteAdapter(
    private val userList: ArrayList<String>,
    private val userIdList: ArrayList<String>,
    private val context: Context
) : RecyclerView.Adapter<VoteAdapter.ViewHolder>() {

    // Other necessary variables
    private var activityID: String = ""
    private var adminId: String = Constants.GlobalData.admin_id

    fun setMyString(string: String) {
        activityID = string
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.vote_item, parent, false)
        return ViewHolder(itemView)
    }
    interface OnButtonClickListener {
        fun onButtonClick(position: Int)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.nameVoteTv.text = userList[position]
        val loyaltyOptions = holder.itemView.context.resources.getStringArray(R.array.Scores)
        val loyaltyAdapter = ArrayAdapter(
            holder.itemView.context,
            android.R.layout.simple_spinner_item,
            loyaltyOptions
        )
        holder.autoCompleteTextViewLoyality.setAdapter(loyaltyAdapter)

        holder.loyalityButton.setOnClickListener {
            val selectedItem = holder.autoCompleteTextViewLoyality.text.toString()
            performPutRequest(userIdList[position], selectedItem.toInt())
            Log.d("oylanan", userIdList[position])
        }


        // Set adapter for fair play spinner
        val fairPlayOptions = holder.itemView.context.resources.getStringArray(R.array.Scores)
        val fairPlayAdapter = ArrayAdapter(
            holder.itemView.context,
            android.R.layout.simple_spinner_item,
            fairPlayOptions
        )
        holder.autoCompleteTextViewFairPlay.setAdapter(fairPlayAdapter)
        holder.fairPlayButton.setOnClickListener {
            val selectedItem = holder.autoCompleteTextViewFairPlay.text.toString()
            performPutRequestFairPlay(userIdList[position], selectedItem.toInt())
        }

        val performanceOptions = holder.itemView.context.resources.getStringArray(R.array.Scores)
        val performanceAdapter = ArrayAdapter(
            holder.itemView.context,
            android.R.layout.simple_spinner_item,
            performanceOptions
        )
        holder.autoCompleteTextViewPerformance.setAdapter(performanceAdapter)
        holder.performanceButton.setOnClickListener {
            val selectedItem = holder.autoCompleteTextViewPerformance.text.toString()
            performPutRequestPerformance(userIdList[position], selectedItem.toInt())
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val loyalityButton: Button = itemView.findViewById(R.id.loyality_Button)
        val fairPlayButton: Button = itemView.findViewById(R.id.fair_play_Button)
        val performanceButton: Button = itemView.findViewById(R.id.performance_Button)
        val autoCompleteTextViewLoyality: AutoCompleteTextView =
            itemView.findViewById(R.id.autoCompleteTextView_Loyality)
        val autoCompleteTextViewFairPlay: AutoCompleteTextView =
            itemView.findViewById(R.id.autoCompleteTextView_fair_play)
        val autoCompleteTextViewPerformance: AutoCompleteTextView =
            itemView.findViewById(R.id.autoCompleteTextView_performance)
        val nameVoteTv: TextView = itemView.findViewById(R.id.name_vote_Tv)
    }


    private fun performPutRequest(oylanan: String, selectedItem: Int) {
        val queue = Volley.newRequestQueue(context)

        val urlString = "${Constants.BASE_URL}${Constants.END_POINT_Vote}$oylanan"

        val json = JSONObject().apply {

            put("loyalty", JSONArray().apply {
                put(JSONObject().apply {
                    put("user_id", adminId)
                    put("activity_id", activityID)
                    put("score", selectedItem)
                })
            })
        }
        val request = object : JsonObjectRequest(Method.PUT, urlString, json,
            { response ->
                Log.d("loyal", response.toString())
            },
            { error ->
                // Handle error
                Log.e("Join", "Error joining room: $error")
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf(
                    "Content-Type" to "application/json"
                )
            }
        }
        queue.add(request)
    }

    private fun performPutRequestFairPlay(oylanan: String, selectedItem: Int) {
        val queue = Volley.newRequestQueue(context)

        val urlString = "${Constants.BASE_URL}${Constants.END_POINT_Vote}$oylanan"

        val json = JSONObject().apply {

            put("fair_play", JSONArray().apply {
                put(JSONObject().apply {
                    put("user_id", adminId)
                    put("activity_id", activityID)
                    put("score", selectedItem)
                })
            })
        }
        val request = object : JsonObjectRequest(Method.PUT, urlString, json,
            { response ->
                Log.d("loyal", response.toString())
            },
            { error ->
                // Handle error
                Log.e("Join", "Error joining room: $error")
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf(
                    "Content-Type" to "application/json"
                )
            }
        }
        queue.add(request)
    }

    private fun performPutRequestPerformance(oylanan: String, selectedItem: Int) {
        val queue = Volley.newRequestQueue(context)

        val urlString = "${Constants.BASE_URL}${Constants.END_POINT_Vote}$oylanan"

        val json = JSONObject().apply {

            put("performance", JSONArray().apply {
                put(JSONObject().apply {
                    put("user_id", adminId)
                    put("activity_id", activityID)
                    put("score", selectedItem)
                })
            })
        }
        val request = object : JsonObjectRequest(Method.PUT, urlString, json,
            { response ->
                Log.d("loyal", response.toString())
            },
            { error ->
                // Handle error
                Log.e("Join", "Error joining room: $error")
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf(
                    "Content-Type" to "application/json"
                )
            }
        }
        queue.add(request)
    }

}






