package com.lnproduction.noveldeglace.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.adapter.ExpandableLayoutAdapter
import com.lnproduction.noveldeglace.model.NovelDetail
import com.lnproduction.noveldeglace.utils.HtmlTextInTextView
import com.lnproduction.noveldeglace.utils.Log
import com.lnproduction.noveldeglace.viewModel.NovelPresenter
import com.squareup.picasso.Picasso

class NovelFragment : BaseFragment() , NovelDetailsView , ExpandableLayoutAdapter.ContentListener {

    companion object {
        fun newInstance(novelSerialize: String): NovelFragment {
            val args = Bundle()
            args.putString("NOVEL",novelSerialize)
            val fragment = NovelFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @BindView(R.id.detail_header)
    lateinit var headerLayout : RelativeLayout
    @BindView(R.id.chapter_layout)
    lateinit var chapterLayout : RelativeLayout
    @BindView(R.id.detail_icon)
    lateinit var imgDetail : ImageView
    @BindView(R.id.title_novel)
    lateinit var titleNovel : TextView
    @BindView(R.id.author_novel)
    lateinit var authorNovel : TextView
    @BindView(R.id.rythm_novel)
    lateinit var rythmNovel : TextView
    @BindView(R.id.chapter_novel)
    lateinit var chapterNovel : TextView
    @BindView(R.id.expandableNovelChapter)
    lateinit var expandableNovelChapter : ExpandableListView

    private lateinit var unbinder: Unbinder

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

        unbinder = ButterKnife.bind(this,view)

        novelPresenter = NovelPresenter()
        novelPresenter.createView(this)
        novelPresenter.getNovelDetails(arguments?.getString("NOVEL").toString())

        return view
    }

    override fun onDestroyView() {
        unbinder.unbind()
        novelPresenter.destroyView()
        super.onDestroyView()
    }

    override fun getErrorMessage(errorMessage: String) {
        Log.e("Erreur", errorMessage)
    }

    override fun getNovelDetails(novelDetail: NovelDetail) {

        val novel = novelPresenter.novel
        activity?.title = novel.novelTitle.titleName
        titleNovel.setTextColor(novel.textColor)
        titleNovel.setText(HtmlTextInTextView(novel.novelTitle.titleName))
        Picasso.with(imgDetail.context).load(novel.imgNovel).fit().centerCrop().into(imgDetail)
        headerLayout.setBackgroundColor(novel.backgroundColor)
        chapterLayout.setBackgroundColor(novel.backgroundColor)


        authorNovel.text = getString(R.string.novel_autor, novelDetail.author)
        authorNovel.setTextColor(novel.textColor)

        rythmNovel.text = getString(R.string.novel_date_out, novelDetail.rythm)
        rythmNovel.setTextColor(novel.textColor)

        chapterNovel.text = getString(R.string.novel_nb_chap, novelDetail.nbChapters)
        chapterNovel.setTextColor(novel.textColor)

        adapter = ExpandableLayoutAdapter(novelDetail.tomeList, novel.backgroundColor,novel.textColor,this)
        expandableNovelChapter.setAdapter(adapter)
    }

    override fun onChapters(url: String) {
        novelPresenter.openWebView(url)
    }

}