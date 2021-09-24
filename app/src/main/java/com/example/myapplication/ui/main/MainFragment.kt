package com.example.myapplication.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.model.Song
import com.example.myapplication.data.remote.ApiServiceImpl
import com.example.myapplication.data.repository.SongRepository
import com.example.myapplication.databinding.MainFragmentBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.adapter.SongAdapter

class MainFragment : BaseFragment() {


    private lateinit var mViewModel: MainViewModel
    private lateinit var mAdapter: SongAdapter

    /**
     * Initialize view Model, it will be called as first method in onViewCreated method
     */
    override fun initializeViewModel() {
        val songDao = AppDatabase.getInstance(requireContext()).songDao
        val viewModelFactory = MainViewModelFactory(SongRepository(ApiServiceImpl(), songDao))
        mViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    /**
     * set Observers to observers for LiveData, will be called after initializing View Model
     */
    override fun setObservers() {
        mViewModel.navDetailSongFrag.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let {
                val action = MainFragmentDirections.actionMainFragmentToSongDetailFragment(it)
                findNavController().navigate(action)
            }

        })
        mViewModel.songData.observe(viewLifecycleOwner, Observer { outerIt ->
            outerIt?.getContentIfNotHandled()?.let {
                mAdapter.setData(it as ArrayList<Song>)
            }
        })
        mViewModel.displayDialog.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let {
                showProgressBar()
            }
        })
        mViewModel.dismissDialog.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let {
                dismissProgressBar()
                mViewModel.getSongList()
            }
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return MainFragmentBinding.inflate(inflater).root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = DataBindingUtil.getBinding<MainFragmentBinding>(view)
        binding?.viewModel = mViewModel

        mAdapter = SongAdapter(mViewModel)
        val manager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding?.rvSongs?.layoutManager = manager
        binding?.rvSongs?.adapter = mAdapter

        mViewModel.initMainFragment()
    }

}