package com.sarftec.lovequotes.presentation.fragment

import android.content.Context
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lovequotes.application.tools.TextSpeaker
import com.sarftec.lovequotes.presentation.adapter.ListItemAdapter
import com.sarftec.lovequotes.presentation.listener.ListActivityListener
import com.sarftec.lovequotes.presentation.viewmodel.ListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class ListFragment : BaseListFragment() {

    private lateinit var activityListener: ListActivityListener

    private val viewModel by activityViewModels<ListViewModel>()

    private val listAdapter by lazy {
        ListItemAdapter(requireContext(), textSpeaker) {
            viewModel.save(it)
        }
    }

    private val textSpeaker by lazy {
        TextSpeaker(requireContext())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ListActivityListener) activityListener = context
    }

    override fun onPause() {
        textSpeaker.stop()
        super.onPause()
    }

    override fun onDestroy() {
        textSpeaker.shutdown()
        super.onDestroy()
    }

    override fun setupRecyclerView(recyclerView: RecyclerView) {
        with(recyclerView) {
            setHasFixedSize(false)
            adapter = listAdapter
        }
    }

    override fun init() {
        lifecycleScope.launch {
            viewModel.getToolbarTitle().collect { title ->
                title?.let {
                    activityListener.setToolbarTitle(it)
                    throw CancellationException()
                }
            }
        }
        viewModel.fetch()
        viewModel.quotes.observe(viewLifecycleOwner) {
            listAdapter.submitData(it)
        }
    }
}