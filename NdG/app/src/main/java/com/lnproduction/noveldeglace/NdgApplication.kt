package com.lnproduction.noveldeglace

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class NdgApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}