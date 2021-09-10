package com.sarftec.lovequotes.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lovequotes.application.model.Category
import com.sarftec.lovequotes.databinding.LayoutMainItemBinding
import com.sarftec.lovequotes.presentation.binding.MainItemBinding

class MainItemAdapter(
    private var items: List<Category> = emptyList(),
    private val itemOnClick: (Category) -> Unit
) : RecyclerView.Adapter<MainItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemViewHolder {
        val layoutBinding = LayoutMainItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainItemViewHolder(layoutBinding)
    }

    override fun onBindViewHolder(holder: MainItemViewHolder, position: Int) {
        holder.bind(MainItemBinding(items[position], itemOnClick))
    }

    override fun getItemCount(): Int = items.size

    fun submitData(newItems: List<Category>) {
        items = newItems
        notifyDataSetChanged()
    }
}