package com.example.iapapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.iapapp.data.BookModel
import com.example.iapapp.databinding.ItemBookBinding

class BookAdapter(private val onClick: (BookModel) -> Unit) :
    ListAdapter<BookModel, BookAdapter.ViewHolder>(MyDiffUtils()) {

    class MyDiffUtils : DiffUtil.ItemCallback<BookModel>() {
        override fun areItemsTheSame(oldItem: BookModel, newItem: BookModel): Boolean {
            return oldItem.bookName == newItem.bookName
        }

        override fun areContentsTheSame(oldItem: BookModel, newItem: BookModel): Boolean {
            return oldItem == newItem
        }

    }

    inner class ViewHolder(private val mBinding: ItemBookBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(model: BookModel) {
            with(mBinding) {
                val context = root.context
                imgBookCover.setImageResource(model.bookCoverImage)
                tvBookTitle.text = model.bookName
                tvBookAuthor.text = model.bookAuthor
                root.setOnClickListener {
                    onClick.invoke(model)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBookBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}