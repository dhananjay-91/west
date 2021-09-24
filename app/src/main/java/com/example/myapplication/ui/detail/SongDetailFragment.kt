package com.example.myapplication.ui.detail

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.data.model.Song
import com.example.myapplication.databinding.SongDetailFragmentBinding
import com.example.myapplication.ui.base.BaseFragment
import kotlinx.android.synthetic.main.song_detail_fragment.*
import java.io.IOException

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class SongDetailFragment : BaseFragment() {


    private lateinit var mViewModel: SongDetailViewModel
    private lateinit var mMediaPlayer: MediaPlayer

    /**
     * Initialize view Model, it will be called as first method in onViewCreated method
     */
    override fun initializeViewModel() {
        mViewModel = ViewModelProvider(this).get(SongDetailViewModel::class.java)
    }

    /**
     * set Observers to observers for LiveData, will be called after initializing View Model
     */
    override fun setObservers() {
        mViewModel.setDetails.observe(viewLifecycleOwner, Observer { outerIt ->
            outerIt?.getContentIfNotHandled()?.let {
                setData(it)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return SongDetailFragmentBinding.inflate(inflater).root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val safeArgs: SongDetailFragmentArgs by navArgs()
        val song = safeArgs.args
        mViewModel.initSongDetail(song)

    }

    private fun setData(song: Song) {
        txtSong.text = song.name
        txtArtist.text = song.artist


        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher_round)

        Glide.with(clParent.context).load(song.image).apply(options).into(imgSong)
        playSong(song.link)

    }

    private fun playSong(url: String) {
        try {
            mMediaPlayer = MediaPlayer()
            mMediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            mMediaPlayer.setDataSource(url)
            mMediaPlayer.prepare()
            mMediaPlayer.start()


            mMediaPlayer.currentPosition
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (this::mMediaPlayer.isInitialized) {
            mMediaPlayer.stop()
            mMediaPlayer.release()
        }
    }

}