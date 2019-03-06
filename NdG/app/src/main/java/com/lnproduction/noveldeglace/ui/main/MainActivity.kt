package com.lnproduction.noveldeglace.ui.main

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.base.BaseActivity
import com.lnproduction.noveldeglace.base.BaseFragment
import com.lnproduction.noveldeglace.base.EmptyFragment
import com.lnproduction.noveldeglace.model.Users
import com.lnproduction.noveldeglace.ui.Login.LoginActivity
import com.lnproduction.noveldeglace.ui.novelList.NovelsFragment
import com.lnproduction.noveldeglace.ui.post.PostFragment
import com.lnproduction.noveldeglace.utils.Constants
import com.lnproduction.noveldeglace.utils.log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_main.*




class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {


    companion object {
        private inline operator fun Int.invoke(message: () -> String) = log(this, TAG, LOG_LEVEL, message)
        private inline operator fun Int.invoke(t: Throwable, message: () -> String) = log(this, TAG, LOG_LEVEL, t, message)

        @Suppress("unused")
        private val TAG = MainActivity::class.java.simpleName
        @Suppress("unused")
        private const val LOG_LEVEL = Constants.LOG_FORCE * Constants.LOG_LEVEL + (1 - Constants.LOG_FORCE) * Log.DEBUG
    }

    private lateinit var searchView: SearchView
    private lateinit var postFragment: PostFragment
    private val LOGIN_CODE = 101
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_main)
        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        setupBottomBar()
        presenter = MainActivityPresenter()
        presenter.createView(this)
        presenter.setNavigationDrawer()
    }

    override fun onDestroy() {
        presenter.destroyView()
        super.onDestroy()
    }

    private fun setupBottomBar() {
        bottom_navigation.inflateMenu(R.menu.bottomview_menu)
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            val id = item.itemId
            var baseFragment = BaseFragment()
            when (id) {
                R.id.action_news -> {
                    baseFragment = PostFragment()
                }
                R.id.action_novel -> {
                    baseFragment = NovelsFragment()
                }
                R.id.action_favorite -> {
                    baseFragment = EmptyFragment()
                }
                R.id.action_vote -> {
                    baseFragment = EmptyFragment()
                }
            }
            supportFragmentManager.beginTransaction().run {
                replace(R.id.sample_content_fragment, baseFragment)
                commit()
            }
            true
        }

        bottom_navigation.menu.getItem(0).isChecked = true
        postFragment = PostFragment()
        supportFragmentManager.beginTransaction().run {
            replace(R.id.sample_content_fragment, postFragment)
            commit()
        }
    }

    fun setupNavigationDrawer(user: Users?) {

        val hView = nav_view.getHeaderView(0)
        val drawerProfile = hView.findViewById(R.id.imageView) as ImageView
        val drawerName = hView.findViewById(R.id.full_name) as TextView
        val userRole = hView.findViewById(R.id.assignment_role) as TextView
        val menu = nav_view.menu

        if(user == null) {
            menu.findItem(R.id.nav_connect).title = getString(R.string.connection_menu_on)
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar_demo)
            val rounded = RoundedBitmapDrawableFactory.create(resources, bitmap)
            rounded.cornerRadius = bitmap.width.toFloat()
            drawerProfile.setImageDrawable(rounded)
            drawerName.text = getString(R.string.profile_status)
            userRole.text = getString(R.string.profile_status)
        }else{
            menu.findItem(R.id.nav_connect).title = getString(R.string.connection_menu_off)
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar_demo)
            val rounded = RoundedBitmapDrawableFactory.create(resources, bitmap)
            rounded.cornerRadius = bitmap.width.toFloat()
            drawerProfile.setImageDrawable(rounded)
            drawerName.text = user.userName
            userRole.text = getString(R.string.profile_status)
        }


        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.action_search)
                ?.actionView as SearchView

        searchView.apply {
            setSearchableInfo(searchManager
                    .getSearchableInfo(componentName))
            maxWidth = Integer.MAX_VALUE

            // listening to search query text change
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    // filter recycler view when query submitted
                    postFragment.filterTextWithQuery(query)
                    return false
                }

                override fun onQueryTextChange(query: String): Boolean {
                    postFragment.filterTextWithQuery(query)
                    return false
                }
            })
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item?.itemId
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //to prevent current item select over and over
        if (item.isChecked) {
            drawer_layout.closeDrawer(GravityCompat.START)
            return false
        }
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_account -> {

            }
            R.id.nav_connect -> {
                if(presenter.isUserConnected())
                    presenter.logout()
                else {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivityForResult(intent, LOGIN_CODE)
                }
            }
            R.id.nav_website -> {
                startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://noveldeglace.com")))
            }
            R.id.nav_twitter -> {
                startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://twitter.com/noveldeglace")))
            }
            R.id.nav_about -> {

            }
            R.id.nav_donation -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == LOGIN_CODE && resultCode == Activity.RESULT_OK)
        {
            presenter.setNavigationDrawer()
        }
    }

}