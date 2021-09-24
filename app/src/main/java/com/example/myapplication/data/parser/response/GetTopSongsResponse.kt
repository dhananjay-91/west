package com.example.myapplication.data.parser.response

import com.example.myapplication.data.model.Song

/**
 * Created by Dhananjay on 10/22/2020
 */
data class GetTopSongsResponse(
    val id: String?,
    val entry: ArrayList<Song>
)