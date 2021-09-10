package com.sarftec.lovequotes.presentation.fragment

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lovequotes.application.tools.TextSpeaker
import com.sarftec.lovequotes.presentation.adapter.ListItemAdapter
import com.sarftec.lovequotes.presentation.listener.MainActivityListener
import com.sarftec.lovequotes.presentation.viewmodel.FavoriteViewModel

class FavoriteFragment : BaseListFragment() {

    private lateinit var activityListener: MainActivityListener

    private val viewModel by viewModels<FavoriteViewModel>()

    private val textSpeaker by lazy {
        TextSpeaker(requireContext())
    }

    private val listAdapter by lazy {
        ListItemAdapter(requireContext(), textSpeaker) {
            viewModel.save(it)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityListener) activityListener = context
    }

    override fun onPause() {
        textSpeaker.stop()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        textSpeaker.shutdown()
    }

    override fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.setHasFixedSize(false)
        recyclerView.adapter = listAdapter
    }

    override fun init() {
        activityListener.setToolbarTitle(viewModel.getToolbarTitle().value)
        viewModel.fetch()
        viewModel.quotes.observe(viewLifecycleOwner) {
            listAdapter.submitData(it)
        }
    }
}