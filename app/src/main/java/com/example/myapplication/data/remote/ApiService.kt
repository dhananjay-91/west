package com.example.myapplication.data.remote

import com.example.myapplication.data.parser.response.GetTopSongsResponse


/**
 * Created by Dhananjay on 10/21/2020
 */
interface ApiService {

    suspend fun getTopSongs(): GetTopSongsResponse

}