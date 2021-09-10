package com.sarftec.lovequotes.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lovequotes.application.model.Quote
import com.sarftec.lovequotes.application.tools.TextSpeaker
import com.sarftec.lovequotes.databinding.LayoutListItemBinding

class ListItemAdapter(
    private val context: Context,
    private val textSpeaker: TextSpeaker,
    private var items: List<Quote> = emptyList(),
    private val onSave: (Quote) -> Unit
) : RecyclerView.Adapter<ListItemViewHolder>(), ViewHolderListener {

    private val adapterPack by lazy {
        Pack(context, textSpeaker)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val layoutBinding = LayoutListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListItemViewHolder(layoutBinding)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bind(items[position], this)
    }

    override fun getItemCount(): Int = items.size

    override fun updateQuote(quote: Quote) {
       onSave(quote)
    }

    override fun getPack(): Pack = adapterPack

    fun submitData(data: List<Quote>) {
        items = data
        notifyDataSetChanged()
    }
}

class Pack(
    val context: Context,
    val textSpeaker: TextSpeaker
)
interface ViewHolderListener {
    fun updateQuote(quote: Quote)
    fun getPack() : Pack
}