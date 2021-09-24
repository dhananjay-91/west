package com.example.myapplication.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.myapplication.data.local.dao.SongDao
import com.example.myapplication.data.model.Song
import com.example.myapplication.data.remote.ApiServiceImpl
import java.lang.Exception

/**
 * Created by Dhananjay on 10/22/2020
 */
class SongRepository(private val apiService: ApiServiceImpl, private val database: SongDao) {

    suspend fun addSongListToDb() {
        val response = apiService.getTopSongs()
        database.addAllSongs(response.entry)
    }

    suspend fun callList(): List<Song> {
        val list = database.getSongList()
        return list
    }
}