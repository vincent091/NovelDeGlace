package com.lnproduction.noveldeglace.activity

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import com.lnproduction.noveldeglace.R


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)
        SystemClock.sleep(2000)
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}