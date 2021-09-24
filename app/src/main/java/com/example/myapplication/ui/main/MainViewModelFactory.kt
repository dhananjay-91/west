package com.example.myapplication.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.local.dao.SongDao
import com.example.myapplication.data.repository.SongRepository
import java.lang.IllegalArgumentException

/**
 * Created by Dhananjay on 10/22/2020
 */
class MainViewModelFactory(
    private val songRepository: SongRepository
) : ViewModelProvider.Factory {
    /**
     * Creates a new instance of the given `Class`.
     *
     *
     *
     * @param modelClass a `Class` whose instance is requested
     * @param <T>        The type parameter for the ViewModel.
     * @return a newly created ViewModel
    </T> */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(songRepository) as T
        }
        throw IllegalArgumentException("MainViewModel class not found")
    }
}