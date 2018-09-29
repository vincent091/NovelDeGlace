package com.lnproduction.noveldeglace.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.utils.Constants
import com.lnproduction.noveldeglace.utils.log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


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
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar?)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.sample_content_fragment, PostFragment())
                commit()
            }
        }
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

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

}