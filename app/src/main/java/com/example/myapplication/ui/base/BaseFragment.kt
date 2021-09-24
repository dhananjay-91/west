package com.example.myapplication.ui.base

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication.R

/**
 * Created by Dhananjay on 10/21/2020
 */
abstract class BaseFragment : Fragment() {


    private lateinit var mProgressDialog: ProgressDialog

    /**
     * Initialize view Model, it will be called as first method in onViewCreated method
     */
    protected abstract fun initializeViewModel()

    /**
     * set Observers to observers for LiveData, will be called after initializing View Model
     */
    protected abstract fun setObservers()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewModel()
        setObservers()
    }


    /**
     * Show progress Dialog while calling web service
     */
    fun showProgressBar() {

        mProgressDialog = ProgressDialog(activity)
        mProgressDialog.setMessage(getString(R.string.please_wait))
        mProgressDialog.setCancelable(false)
        mProgressDialog.show()
    }

    /**
     * Dismiss progress bar
     */
    fun dismissProgressBar() {
        if (::mProgressDialog.isInitialized) {
            mProgressDialog.let { if (mProgressDialog.isShowing) mProgressDialog.dismiss() }
        }
    }
}