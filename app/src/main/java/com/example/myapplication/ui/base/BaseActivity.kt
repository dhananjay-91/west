package com.example.myapplication.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Dhananjay on 10/21/2020
 */
abstract class BaseActivity : AppCompatActivity() {


    protected abstract fun initializeViewModel()
    protected abstract fun initializeViewBinding()
    protected abstract fun observeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewBinding()

        initializeViewModel()
        observeViewModel()
    }

}