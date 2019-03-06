package com.lnproduction.noveldeglace.ui.novelDetails

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.base.BaseFragment
import com.lnproduction.noveldeglace.model.NovelDetail
import com.lnproduction.noveldeglace.utils.HtmlTextInTextView
import com.lnproduction.noveldeglace.utils.Log
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.novel_detail.*

class NovelFragment : BaseFragment() , NovelDetailsView, ExpandableLayoutAdapter.ContentListener {

    companion object {
        fun newInstance(novelSerialize: String): NovelFragment {
            val args = Bundle()
            args.putString("NOVEL",novelSerialize)
            val fragment = NovelFragment()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var novelPresenter: NovelPresenter
    lateinit var adapter : ExpandableListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        // You can hide the state of the menu item here if you call getActivity().supportInvalidateOptionsMenu(); somewhere in your code
        val menuItem = menu.findItem(R.id.action_search)
        menuItem.isVisible = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.novel_detail, container,false)

        novelPresenter = NovelPresenter()
        novelPresenter.createView(this)
        novelPresenter.getNovelDetails(arguments?.getString("NOVEL").toString())

        return view
    }

    override fun onDestroyView() {
        novelPresenter.destroyView()
        super.onDestroyView()
    }

    override fun getErrorMessage(errorMessage: String) {
        Log.e("Erreur", errorMessage)
    }

    override fun getNovelDetails(novelDetail: NovelDetail) {

        val novel = novelPresenter.novel
        activity?.title = novel.novelTitle.titleName
        favorite_icon.setColorFilter(novel.textColor)
        title_novel.setTextColor(novel.textColor)
        title_novel.setText(HtmlTextInTextView(novel.novelTitle.titleName))
        Picasso.with(detail_icon.context).load(novel.imgNovel).fit().centerCrop().into(detail_icon)
        detail_header.setBackgroundColor(novel.backgroundColor)
        chapter_layout.setBackgroundColor(novel.backgroundColor)


        author_novel.text = getString(R.string.novel_autor, novelDetail.author)
        author_novel.setTextColor(novel.textColor)

        rythm_novel.text = getString(R.string.novel_date_out, novelDetail.rythm)
        rythm_novel.setTextColor(novel.textColor)

        chapter_novel.text = getString(R.string.novel_nb_chap, novelDetail.nbChapters)
        chapter_novel.setTextColor(novel.textColor)

        synpsys_novel.text = getString(R.string.novel_synopsys,novelDetail.synopsis)
        synpsys_novel.setTextColor(novel.textColor)

        adapter = ExpandableLayoutAdapter(novelDetail.tomeList, novel.backgroundColor, novel.textColor, this)
        expandableNovelChapter.setAdapter(adapter)
        expandableNovelChapter.expandGroup(0)
        expandableNovelChapter.divider = ColorDrawable(novel.backgroundColor)
        expandableNovelChapter.dividerHeight = 20
    }

    override fun onChapters(url: String) {
        novelPresenter.openWebView(url)
    }

}