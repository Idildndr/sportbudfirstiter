package com.example.sportbud3

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportbud3.Adapters.FavAdapter
import com.example.sportbud3.Constants.BASE_URL
import com.example.sportbud3.Constants.GlobalData.fav_arrayList
import com.example.sportbud3.Model.UserItem
import com.example.sportbud3.Model.UserResponceItem
import com.example.sportbud3.services.MyApiService
import com.example.sportbud3.services.RetrofitInstance
import kotlinx.android.synthetic.main.profile_2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileFragment : Fragment() {

    private lateinit var favadapter: FavAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usernametext_user_p.text = Constants.GlobalData.userName + " " + Constants.GlobalData.userLastName
        textView4.text = Constants.GlobalData.about


        favadapter = FavAdapter(Constants.GlobalData.fav_arrayList)
        recyclerViewFav.adapter = favadapter
        recyclerViewFav.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
}


