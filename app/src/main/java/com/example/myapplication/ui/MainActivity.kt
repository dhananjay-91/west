package com.example.myapplication.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.MainActivityBinding
import com.example.myapplication.ui.base.BaseActivity

class MainActivity : BaseActivity() {


    override fun initializeViewModel() {
    }

    override fun initializeViewBinding() {
        DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)
    }

    override fun observeViewModel() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}