package com.lnproduction.noveldeglace.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.lnproduction.noveldeglace.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_main)
        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.sample_content_fragment, PostFragment())
                commit()
            }
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val hView = navigationView.getHeaderView(0)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar_demo)

        val rounded = RoundedBitmapDrawableFactory.create(resources, bitmap)

        rounded.setCornerRadius(bitmap.width.toFloat())

        val drawerProfile = hView.findViewById(R.id.imageView) as ImageView
        drawerProfile.setImageDrawable(rounded)

        val drawerName = hView.findViewById(R.id.full_name) as TextView
        drawerName.setText("Claramiel")

        val userRole = hView.findViewById(R.id.assignment_role) as TextView
        userRole.setText("Lolli gothique")

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item?.itemId
        if(R.id.action_mode == id)
        {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        /*when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }*/

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}