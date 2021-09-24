package com.example.myapplication.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.myapplication.data.model.Song

/**
 * Created by Dhananjay on 10/22/2020
 */
@Dao
interface SongDao {

    @Insert(onConflict = REPLACE)
    suspend fun addSong(song: Song)

    @Insert(onConflict = REPLACE)
    suspend fun addAllSongs(songs: ArrayList<Song>)

    @Query("SELECT * FROM song")
    suspend fun getSongList(): List<Song>


}