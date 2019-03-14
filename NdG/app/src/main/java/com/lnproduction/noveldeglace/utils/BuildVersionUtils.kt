package com.lnproduction.noveldeglace.utils

import android.os.Build
import android.text.Html

fun htmlTextInTextView(text : String) : String
{
    return if (Build.VERSION.SDK_INT >= 24) {
        Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(text).toString()
    }
}