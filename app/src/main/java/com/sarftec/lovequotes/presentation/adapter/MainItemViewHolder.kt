package com.sarftec.lovequotes.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lovequotes.databinding.LayoutMainItemBinding
import com.sarftec.lovequotes.presentation.binding.MainItemBinding

class MainItemViewHolder(
    private val layoutBinding: LayoutMainItemBinding
) : RecyclerView.ViewHolder(layoutBinding.root) {

    fun bind(mainItemBinding: MainItemBinding) {
        layoutBinding.binding = mainItemBinding
        layoutBinding.executePendingBindings()
    }
}