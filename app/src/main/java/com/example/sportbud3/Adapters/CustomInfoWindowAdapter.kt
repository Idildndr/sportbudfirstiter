package com.example.sportbud3.Adapters

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.ContentView
import com.example.sportbud3.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext


class CustomInfoWindowAdapter(context: Context):GoogleMap.InfoWindowAdapter {
    private val contentView = (context as Activity).layoutInflater.inflate(R.layout.info_window,null )
    override fun getInfoContents(marker: Marker): View? {
        renderView(marker,contentView)
        return contentView
    }

    override fun getInfoWindow(marker: Marker): View? {
        renderView(marker,contentView)
        return contentView
    }


    private fun renderView(marker:Marker?,contentView: View){
        val title = marker?.title
        val subTitle = marker?.snippet

        val titletext = contentView.findViewById<TextView>(R.id.title)
        titletext.text =  title

        val subTitleText= contentView.findViewById<TextView>(R.id.subtitle)
        subTitleText.text =  subTitle



    }
}