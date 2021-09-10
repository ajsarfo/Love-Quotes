package com.sarftec.lovequotes.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lovequotes.application.model.Quote
import com.sarftec.lovequotes.databinding.LayoutListItemBinding
import com.sarftec.lovequotes.presentation.binding.ListItemBinding

class ListItemViewHolder(
    private val layoutBinding: LayoutListItemBinding
) : RecyclerView.ViewHolder(layoutBinding.root) {

    fun bind(quote: Quote, viewHolderListener: ViewHolderListener) {
        layoutBinding.binding = ListItemBinding(quote, viewHolderListener).also {
            it.init()
        }
        layoutBinding.executePendingBindings()
    }
}