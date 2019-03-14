package com.lnproduction.noveldeglace.utils

import android.annotation.StringRes
import android.content.res.Resources

class ResourceProvider(private var resources: Resources) {

    fun getString(@StringRes stringResId: Int): String {
        return resources.getString(stringResId)
    }
}