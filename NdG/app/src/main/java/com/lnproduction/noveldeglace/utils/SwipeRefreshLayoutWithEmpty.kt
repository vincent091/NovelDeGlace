package com.lnproduction.noveldeglace.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class SwipeRefreshLayoutWithEmpty : SwipeRefreshLayout {
    private val container: ViewGroup? by lazy {

        for (i in 0 until childCount) {
            if (getChildAt(i) is ViewGroup) {
                return@lazy getChildAt(i) as ViewGroup
            }
        }

        throw RuntimeException("Container view not found")
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun canChildScrollUp(): Boolean {
        // The swipe refresh layout has 2 children; the circle refresh indicator
        // and the view container. The container is needed here
        container?.apply {
            for (i in 0 until childCount) {
                val view = getChildAt(i)
                if (view.visibility == View.VISIBLE) {
                    return view.canScrollVertically(-1)
                }
            }
        }

        return false
    }
}