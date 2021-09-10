package com.sarftec.lovequotes.presentation.fragment

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lovequotes.application.file.vibrate
import com.sarftec.lovequotes.application.manager.InterstitialManager
import com.sarftec.lovequotes.application.manager.NetworkManager
import com.sarftec.lovequotes.presentation.adapter.MainItemAdapter
import com.sarftec.lovequotes.presentation.listener.MainActivityListener
import com.sarftec.lovequotes.presentation.viewmodel.MainViewModel

class MainFragment : BaseListFragment() {

    val viewModel by viewModels<MainViewModel>()

    private lateinit var activityListener: MainActivityListener

    private val mainItemAdapter by lazy {
        MainItemAdapter {
            requireContext().vibrate()
           interstitialManager.showAd {
               activityListener.navigate(it)
           }
        }
    }

    private val interstitialManager by lazy {
        InterstitialManager(
            requireActivity(),
            NetworkManager(requireContext()),
            listOf(1, 3, 4, 3)
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityListener) activityListener = context
    }

    override fun init() {
        activityListener.setToolbarTitle(viewModel.getToolbarTitle().value)
        viewModel.fetch()
        viewModel.categories.observe(viewLifecycleOwner) {
            mainItemAdapter.submitData(it)
        }
    }

    override fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            setHasFixedSize(true)
            adapter = mainItemAdapter
        }
    }
}