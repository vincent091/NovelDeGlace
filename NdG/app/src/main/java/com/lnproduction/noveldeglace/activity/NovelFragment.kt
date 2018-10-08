package com.lnproduction.noveldeglace.activity

import android.os.Build
import android.os.Bundle
import android.text.Html
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
import com.lnproduction.noveldeglace.model.Novel
import com.lnproduction.noveldeglace.model.NovelDetail
import com.lnproduction.noveldeglace.utils.Log
import com.lnproduction.noveldeglace.utils.SchedulersFactory
import com.lnproduction.noveldeglace.viewModel.NovelPresenter
import com.squareup.picasso.Picasso

class NovelFragment : BaseFragment() , NovelDetailsView {

    companion object {
        private val BACKGROUND_COLOR = "palette_background"
        private val TEXT_COLOR = "text_color"
        private val IMAGE_DETAIL = "image_detail"
        private val TITLE_NOVEL = "title_novel"
        private val TITLE_ID = "title_id"


        fun newInstance(novel: Novel): NovelFragment {
            val args = Bundle()
            args.putInt(BACKGROUND_COLOR, novel.palette.lightMutedSwatch!!.rgb)
            args.putInt(TEXT_COLOR, novel.palette.darkMutedSwatch!!.rgb)
            args.putString(IMAGE_DETAIL, novel.imgNovel)
            args.putString(TITLE_NOVEL, novel.novelTitle.titleName)
            args.putInt(TITLE_ID, novel.novelId)
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

        novelPresenter = NovelPresenter(SchedulersFactory())
        novelPresenter.createView(this)
        novelPresenter.getNovelDetails(arguments!!.getInt(TITLE_ID))



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

        when {
            arguments!!.containsKey(TITLE_NOVEL) && arguments!!.containsKey(TEXT_COLOR) -> {
                titleNovel.setTextColor(arguments!!.getInt(TEXT_COLOR))
                if (Build.VERSION.SDK_INT >= 24)
                {
                    titleNovel.setText(Html.fromHtml(arguments!!.getString(TITLE_NOVEL), Html.FROM_HTML_MODE_LEGACY))
                }
                else
                {
                    titleNovel.setText(Html.fromHtml(arguments!!.getString(TITLE_NOVEL)));
                }
                activity?.title = titleNovel.text
            }
        }
        when{
            arguments!!.containsKey(IMAGE_DETAIL) -> Picasso.with(imgDetail.context).load(arguments!!.getString(IMAGE_DETAIL)).fit().centerCrop().into(imgDetail)
        }

        when{
            arguments!!.containsKey(BACKGROUND_COLOR) -> {
                headerLayout.setBackgroundColor(arguments!!.getInt(BACKGROUND_COLOR))
                chapterLayout.setBackgroundColor(arguments!!.getInt(BACKGROUND_COLOR))
            }
        }

        authorNovel.text = getString(R.string.novel_autor, novelDetail.author)
        authorNovel.setTextColor(arguments!!.getInt(TEXT_COLOR))

        rythmNovel.text = getString(R.string.novel_date_out, novelDetail.rythm)
        rythmNovel.setTextColor(arguments!!.getInt(TEXT_COLOR))

        chapterNovel.text = getString(R.string.novel_nb_chap, novelDetail.nbChapters)
        chapterNovel.setTextColor(arguments!!.getInt(TEXT_COLOR))

        adapter = ExpandableLayoutAdapter(novelDetail.tomeList, arguments!!.getInt(BACKGROUND_COLOR),arguments!!.getInt(TEXT_COLOR))
        expandableNovelChapter.setAdapter(adapter)
    }

}