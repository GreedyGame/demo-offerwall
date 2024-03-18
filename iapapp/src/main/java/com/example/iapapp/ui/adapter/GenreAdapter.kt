package com.example.iapapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.iapapp.R
import com.example.iapapp.data.GenreModel
import com.example.iapapp.databinding.ItemGenreBinding

class GenreAdapter(private val onClick: (GenreModel) -> Unit) :
    ListAdapter<GenreModel, GenreAdapter.ViewHolder>(MyDiffUtils()) {

    class MyDiffUtils : DiffUtil.ItemCallback<GenreModel>() {
        override fun areItemsTheSame(oldItem: GenreModel, newItem: GenreModel): Boolean {
            return oldItem.genreName == newItem.genreName
        }

        override fun areContentsTheSame(oldItem: GenreModel, newItem: GenreModel): Boolean {
            return oldItem == newItem
        }

    }

    inner class ViewHolder(private val mBinding: ItemGenreBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(model: GenreModel) {
            with(mBinding) {
                val context = root.context
                tvGenre.text = model.genreName
                val bgColor = if (model.isSelected) {
                    R.color.tab_active
                } else {
                    R.color.tab_inactive
                }
                card.setCardBackgroundColor(
                    context.resources.getColor(
                        bgColor, null
                    )
                )
                val textColor = if (model.isSelected) {
                    R.color.tab_inactive
                } else {
                    R.color.tab_active
                }
                tvGenre.setTextColor(
                    context.resources.getColor(
                        textColor, null
                    )
                )
                root.setOnClickListener {
                    onClick.invoke(model)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGenreBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}