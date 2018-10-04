package com.lnproduction.noveldeglace.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.model.Novel
import com.squareup.picasso.Picasso

class NovelFragment : BaseFragment() {

    companion object {
        private val BACKGROUND_COLOR = "palette_background"
        private val TEXT_COLOR = "text_color"
        private val IMAGE_DETAIL = "image_detail"
        private val TITLE_NOVEL = "title_novel"

        fun newInstance(novel: Novel): NovelFragment {
            val args = Bundle()
            args.putInt(BACKGROUND_COLOR, novel.palette.lightMutedSwatch!!.rgb)
            args.putInt(TEXT_COLOR, novel.palette.darkMutedSwatch!!.rgb)
            args.putString(IMAGE_DETAIL, novel.imgNovel)
            args.putString(TITLE_NOVEL, novel.novelTitle.titleName)
            val fragment = NovelFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var headerLayout : RelativeLayout
    private lateinit var chapterLayout : RelativeLayout
    private lateinit var imgDetail : ImageView
    private lateinit var titleNovel : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        // You can hide the state of the menu item here if you call getActivity().supportInvalidateOptionsMenu(); somewhere in your code
        val menuItem = menu.findItem(R.id.action_search)
        menuItem.setVisible(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.novel_detail, container,false)
        headerLayout = view.findViewById(R.id.detail_header)
        chapterLayout = view.findViewById(R.id.chapter_layout)
        imgDetail = view.findViewById(R.id.detail_icon)
        titleNovel = view.findViewById(R.id.title_novel)

        when {
            arguments!!.containsKey(TITLE_NOVEL) && arguments!!.containsKey(TEXT_COLOR) -> {
                titleNovel.setTextColor(arguments!!.getInt(TEXT_COLOR))
                titleNovel.text = arguments!!.getString(TITLE_NOVEL)
                activity?.setTitle(titleNovel.text)
            }
            arguments!!.containsKey(IMAGE_DETAIL) -> Picasso.with(imgDetail.context).load(arguments!!.getString(IMAGE_DETAIL)).fit().centerCrop().into(imgDetail)

            arguments!!.containsKey(BACKGROUND_COLOR) -> {
                headerLayout.setBackgroundColor(arguments!!.getInt(BACKGROUND_COLOR))
                chapterLayout.setBackgroundColor(arguments!!.getInt(BACKGROUND_COLOR))
            }
        }



        return view
    }

}