package com.lnproduction.noveldeglace.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.model.Novel
import com.lnproduction.noveldeglace.utils.Constants
import com.lnproduction.noveldeglace.utils.log
import com.lnproduction.noveldeglace.viewModel.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity(), IMainActivity {

    private val presenter = MainActivityPresenter(this)

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
        presenter.onCreate()
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
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun getErrorMessage(errorMessage: String) {
        Log.e("MainActivity", errorMessage)
    }

    override fun getNovels(titleNovel: ArrayList<Novel>?) {
        /*recycler_view_notice_list.apply {

        }*/
    }

}