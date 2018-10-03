package com.lnproduction.noveldeglace.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.model.Novel

class NovelFragment : BaseFragment() {

    companion object {
        private val BACKGROUND_COLOR = "palette_background"

        fun newInstance(novel: Novel): NovelFragment {
            val args = Bundle()
            args.putInt(BACKGROUND_COLOR, novel.palette.lightMutedSwatch!!.rgb)
            val fragment = NovelFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var headerLayout : RelativeLayout
    private lateinit var chapterLayout : RelativeLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.novel_detail, container,false)
        headerLayout = view.findViewById(R.id.detail_header)
        chapterLayout = view.findViewById(R.id.chapter_layout)

        if(arguments!!.containsKey(BACKGROUND_COLOR)){
            headerLayout.setBackgroundColor(arguments!!.getInt(BACKGROUND_COLOR))
            chapterLayout.setBackgroundColor(arguments!!.getInt(BACKGROUND_COLOR))
        }

        return view
    }

}