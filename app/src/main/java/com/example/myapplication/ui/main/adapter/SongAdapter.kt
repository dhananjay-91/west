package com.example.myapplication.ui.main.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.myapplication.R
import com.example.myapplication.data.model.Song
import com.example.myapplication.databinding.SongsItemLayoutBinding


/**
 * Created by Dhananjay on 10/23/2020
 */
class SongAdapter(private val itemListener: RecyclerItemListener) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {



    private var mSongs = ArrayList<Song>()

    fun setData(list: ArrayList<Song>) {

        mSongs.clear()
        mSongs.addAll(list)
        notifyDataSetChanged()
    }


    class SongViewHolder(private val binding: SongsItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(song: Song, itemListener: RecyclerItemListener) {
            binding.textView.text = song.name

            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)

            Glide.with(binding.root.context).load(song.image).apply(options).into(binding.imageView)


            binding.root.setOnClickListener { v ->

                itemListener.onItemSelected(song)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = DataBindingUtil.inflate<SongsItemLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.songs_item_layout, parent, false
        )
        return SongViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = mSongs[position]
        holder.bind(song, itemListener)
    }


    override fun getItemCount(): Int {
        return mSongs.size
    }

    interface RecyclerItemListener {
        fun onItemSelected(song: Song)
    }
}