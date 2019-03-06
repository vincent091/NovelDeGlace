package com.lnproduction.noveldeglace.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.base.BaseFragment


class EmptyFragment : BaseFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.empty_fragment, container, false)
        activity?.setTitle("Vide")

        return view
    }
}