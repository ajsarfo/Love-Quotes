package com.sarftec.lovequotes.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.commit
import com.appodeal.ads.Appodeal
import com.sarftec.lovequotes.R
import com.sarftec.lovequotes.databinding.ActivityListBinding
import com.sarftec.lovequotes.presentation.fragment.ListFragment
import com.sarftec.lovequotes.presentation.listener.ListActivityListener
import com.sarftec.lovequotes.presentation.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListActivity : BaseActivity(), ListActivityListener {

    private val binding by lazy {
        ActivityListBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    private val viewModel by viewModels<ListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.listToolbar)
        savedInstanceState ?: kotlin.run {
            viewModel.setBundle(intent.getBundleExtra(ACTIVITY_BUNDLE))
        }
        binding.listToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        supportFragmentManager.commit {
            replace(R.id.fragment_container, ListFragment())
            setReorderingAllowed(true)
        }
    }

    override fun onResume() {
        super.onResume()
        Appodeal.show(this, Appodeal.BANNER_VIEW)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.list_menu, menu)
        setupSearchView(menu.findItem(R.id.search).actionView as SearchView)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.filter(newText)
                    return true
                }
            }
        )
    }

    override fun setToolbarTitle(title: String?) {
        binding.listToolbar.title = title
    }
}