package com.example.myapplication.data.remote

/**
 * Created by Dhananjay on 10/22/2020
 */
object EndPoint {


    // Get top songs
    public fun getSongUrl(top: Int = 20): String {
        return "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=$top/xml"
    }

}