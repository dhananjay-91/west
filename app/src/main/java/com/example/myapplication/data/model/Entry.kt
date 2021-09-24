package com.example.myapplication.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by Dhananjay on 10/21/2020
 */
@Parcelize
@Entity(tableName = "song")
data class Song(
    @PrimaryKey val name: String,
    val image: String,
    val link: String,
    val artist: String
) : Parcelable