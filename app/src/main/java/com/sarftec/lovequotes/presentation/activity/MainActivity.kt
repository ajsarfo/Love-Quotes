package com.sarftec.lovequotes.presentation.activity

import android.app.UiModeManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.appodeal.ads.Appodeal
import com.appodeal.ads.utils.Log
import com.sarftec.lovequotes.R
import com.sarftec.lovequotes.application.file.*
import com.sarftec.lovequotes.application.model.Category
import com.sarftec.lovequotes.databinding.ActivityMainBinding
import com.sarftec.lovequotes.databinding.LayoutRatingsDialogBinding
import com.sarftec.lovequotes.databinding.RateAppMenuItemBinding
import com.sarftec.lovequotes.presentation.fragment.FavoriteFragment
import com.sarftec.lovequotes.presentation.fragment.MainFragment
import com.sarftec.lovequotes.presentation.listener.MainActivityListener
import com.sarftec.lovequotes.presentation.manager.RatingsManager
import com.sarftec.lovequotes.presentation.tools.RatingsDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity(), MainActivityListener {

    private val binding by lazy {
        ActivityMainBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    private val ratingsMenuItem by lazy {
        RateAppMenuItemBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    private val ratingsManager by lazy {
        RatingsManager(
            lifecycleScope,
            RatingsDialog(
                LayoutRatingsDialogBinding.inflate(
                    LayoutInflater.from(this@MainActivity),
                    binding.root,
                    false
                )
            )
        )
    }

    private val drawerToggle by lazy {
        ActionBarDrawerToggle(
            this@MainActivity,
            binding.navigationDrawer,
            R.string.open_drawer,
            R.string.close_drawer
        )
    }

    private var fragmentSelected = false

    private var fragmentId: Int = MAIN_FRAGMENT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //Setting Appodeal configurations
        Appodeal.setBannerViewId(R.id.main_banner)
        Appodeal.initialize(
            this,
            getString(R.string.appodeal_app_id),
            Appodeal.BANNER_VIEW or Appodeal.INTERSTITIAL
        )
        savedInstanceState?.run {
            setFragment(
                if (getInt(CURRENT_FRAGMENT) == MAIN_FRAGMENT) MainFragment()
                else ListFragment()
            )
        } ?: setFragment(MainFragment(), true)
        setSupportActionBar(binding.mainToolbar)
        setupNavigationDrawer()
        setupNavigationView()
        configureDarkMode()
        lifecycleScope.launchWhenCreated {
            ratingsManager.init()
        }
    }

    override fun onResume() {
        super.onResume()
        Appodeal.show(this, Appodeal.BANNER_VIEW)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(
            CURRENT_FRAGMENT,
            if (supportFragmentManager.fragments[0] is MainFragment) MAIN_FRAGMENT
            else FAVORITE_FRAGMENT
        )
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        menu?.let {
            it.findItem(R.id.rate_app).actionView = ratingsMenuItem.root
            ratingsMenuItem.rateApp.setOnClickListener {
                ratingsManager.onRate()
            }
            ratingsManager.startRateAppAnimation(ratingsMenuItem.rateApp)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    override fun navigate(category: Category) {
        val bundle = Bundle().apply {
            putInt(CATEGORY_ID, category.id)
        }
        navigateTo(ListActivity::class.java, bundle = bundle)
    }

    private fun setFragment(fragment: Fragment, add: Boolean = false) {
        supportFragmentManager.commit {
            if (add) add(R.id.fragment_container, fragment)
            else replace(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
        }
    }

    private fun configureDarkMode() {
        val appModeMenuItem = binding.navigationView.menu.findItem(R.id.app_mode)
        appModeMenuItem.title = when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_YES -> getString(R.string.light_mode)
            else -> getString(R.string.night_mode)
        }
    }

    private fun switchDarkMode() {
        val uiManager = getSystemService(UI_MODE_SERVICE) as UiModeManager
        val appModeMenuItem = binding.navigationView.menu.findItem(R.id.app_mode)
        AppCompatDelegate.setDefaultNightMode(
            when (uiManager.nightMode) {
                UiModeManager.MODE_NIGHT_YES -> {
                    uiManager.nightMode = UiModeManager.MODE_NIGHT_NO
                    appModeMenuItem.title = getString(R.string.night_mode)
                    UiModeManager.MODE_NIGHT_NO.toInt()
                }
                else -> {
                    uiManager.nightMode = UiModeManager.MODE_NIGHT_YES
                    appModeMenuItem.title = getString(R.string.light_mode)
                    UiModeManager.MODE_NIGHT_YES.toInt()
                }
            }
        )
    }

    private fun setupNavigationDrawer() {
        with(binding.navigationDrawer) {
            drawerToggle.drawerArrowDrawable.color = ContextCompat.getColor(
                this@MainActivity, R.color.white
            )
            drawerToggle.setToolbarNavigationClickListener {
                openDrawer(GravityCompat.START)
            }
            addDrawerListener(drawerToggle)
            drawerToggle.syncState()
            addDrawerListener(
                object : DrawerLayout.DrawerListener {
                    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

                    }

                    override fun onDrawerOpened(drawerView: View) {

                    }

                    override fun onDrawerClosed(drawerView: View) {
                        if (!fragmentSelected) return
                        fragmentSelected = false
                        when (fragmentId) {
                            MAIN_FRAGMENT -> setFragment(MainFragment())
                            FAVORITE_FRAGMENT -> setFragment(FavoriteFragment())
                        }
                    }

                    override fun onDrawerStateChanged(newState: Int) {}
                }
            )
        }
    }

    private fun setupNavigationView() {
        assets.open("images/background.jpg").use {
            binding.navigationView.getHeaderView(0)
                .findViewById<ImageView>(R.id.header_image)
                .setImageBitmap(BitmapFactory.decodeStream(it))
        }
        setupNavigationMenu()
    }

    private fun setupNavigationMenu() {
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.love_quote -> {
                    fragmentSelected = true
                    fragmentId = if (fragmentId == MAIN_FRAGMENT) NO_FRAGMENT else {
                        vibrate()
                        MAIN_FRAGMENT
                    }
                    binding.navigationDrawer.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.favorite_qutoes -> {
                    fragmentSelected = true
                    fragmentId = if (fragmentId == FAVORITE_FRAGMENT) NO_FRAGMENT else {
                        vibrate()
                        FAVORITE_FRAGMENT
                    }
                    binding.navigationDrawer.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.rate_app -> {
                    vibrate()
                    rateApp()
                    lifecycleScope.launch {
                        editSettings(SHOW_RATINGS, false)
                    }
                    true
                }

                R.id.more_apps -> {
                    moreApps()
                    true
                }

                R.id.app_mode -> {
                    vibrate()
                    switchDarkMode()
                    true
                }
                else -> false
            }
        }
    }

    override fun setToolbarTitle(title: String?) {
        binding.mainToolbar.title = title
    }

    override fun onBackPressed() {
        super.onBackPressed()
        with(binding.navigationDrawer) {
            if (isDrawerOpen(GravityCompat.START))
                closeDrawer(GravityCompat.START)
        }
    }

    companion object {
        const val CURRENT_FRAGMENT = "current_fragment_id"
        const val NO_FRAGMENT = 0
        const val MAIN_FRAGMENT = 1
        const val FAVORITE_FRAGMENT = 2
    }
}