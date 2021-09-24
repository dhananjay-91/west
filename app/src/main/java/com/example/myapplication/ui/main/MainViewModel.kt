package com.example.myapplication.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Song
import com.example.myapplication.data.repository.SongRepository
import com.example.myapplication.ui.base.BaseViewModel
import com.example.myapplication.ui.main.adapter.SongAdapter
import com.example.myapplication.utils.Event
import kotlinx.coroutines.launch

class MainViewModel(private val songRepository: SongRepository) :
    BaseViewModel(), SongAdapter.RecyclerItemListener {

    companion object {
        private const val TAG = "MainViewModel"
    }

    // navigate to Details screen
    private var mNavDetailSongFragment = MutableLiveData<Event<Song>>()
    val navDetailSongFrag: LiveData<Event<Song>> = mNavDetailSongFragment

    //set data to adatper
    private var mSongData = MutableLiveData<Event<List<Song>>>()
    val songData: LiveData<Event<List<Song>>> = mSongData

    // show dialog
    private var mDisplayDialog = MutableLiveData<Event<Unit>>()
    val displayDialog: LiveData<Event<Unit>> = mDisplayDialog

    // dismiss dialog
    private var mDismissDialog = MutableLiveData<Event<Unit>>()
    val dismissDialog: LiveData<Event<Unit>> = mDismissDialog

    fun initMainFragment() {

        viewModelScope.launch {
            mDisplayDialog.postValue(Event(Unit))
            val songs = songRepository.addSongListToDb()
            Log.e(TAG, songs.toString())
            mDismissDialog.postValue(Event(Unit))
        }
    }

    fun getSongList() {

        viewModelScope.launch {

            val list = songRepository.callList()
            mSongData.postValue(Event(list))
        }

    }

    // song adapter click listener
    override fun onItemSelected(song: Song) {
        mNavDetailSongFragment.value = Event(song)
    }

}