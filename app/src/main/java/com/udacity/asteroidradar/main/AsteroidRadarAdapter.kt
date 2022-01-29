package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.ListViewMainBinding

class AsteroidRadarAdapter(private  var onClickListener: OnClickListener):
    ListAdapter<Asteroid, AsteroidRadarAdapter.AsteroidRadarViewHolder> (DiffCallBack) {

    class AsteroidRadarViewHolder ( private var binding:ListViewMainBinding)
        : RecyclerView.ViewHolder(binding.root){
            fun bind (asteroid: Asteroid) {
                binding.property = asteroid
                binding.executePendingBindings()
            }
        }

    companion object DiffCallBack: DiffUtil.ItemCallback<Asteroid>(){
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidRadarViewHolder {
        return AsteroidRadarViewHolder(ListViewMainBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AsteroidRadarViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.itemView.setOnClickListener {
          onClickListener.onClick(asteroid)
        }

        holder.bind(asteroid)
    }

    class OnClickListener (val clickListener: (asteroid: Asteroid) -> Unit ){
        fun onClick (asteroid: Asteroid) = clickListener(asteroid)
    }
}