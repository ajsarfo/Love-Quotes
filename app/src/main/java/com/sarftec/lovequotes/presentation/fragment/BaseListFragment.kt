package com.sarftec.lovequotes.presentation.fragment

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lovequotes.databinding.LayoutRecyclerViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseListFragment : Fragment() {

    protected lateinit var binding: LayoutRecyclerViewBinding

    protected abstract fun setupRecyclerView(recyclerView: RecyclerView)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutRecyclerViewBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        init()
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                BottomMarginItemDecorator(
                    8f,
                    requireContext()
                )
            )
            savedInstanceState ?: setupRecyclerView(this)
        }
        return binding.root
    }

    protected abstract fun init()

    private class BottomMarginItemDecorator(
        private var padding: Float,
        context: Context
    ) : RecyclerView.ItemDecoration() {

        init {
            padding = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                padding,
                context.resources.displayMetrics
            )
        }

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            val itemPosition = parent.getChildAdapterPosition(view)
            if(itemPosition == RecyclerView.NO_POSITION) return
            val adapter = parent.adapter ?: return
            if(itemPosition == adapter.itemCount - 1) {
                outRect.bottom = padding.toInt()
            }
        }
    }
}