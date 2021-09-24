package com.example.myapplication.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.model.Song
import com.example.myapplication.ui.base.BaseViewModel
import com.example.myapplication.utils.Event

class SongDetailViewModel : BaseViewModel() {

    private var mSetDetails = MutableLiveData<Event<Song>>()
    val setDetails: LiveData<Event<Song>> = mSetDetails


    fun initSongDetail(song: Song) {
        mSetDetails.value = Event(song)
    }
}