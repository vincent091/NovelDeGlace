package com.lnproduction.noveldeglace.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.adapter.NovelAdapter
import com.lnproduction.noveldeglace.model.Novel
import com.lnproduction.noveldeglace.utils.AutoFitGridLayoutManager
import com.lnproduction.noveldeglace.utils.MultiLineRadioGroup
import com.lnproduction.noveldeglace.utils.RecyclerItemDecoration
import com.lnproduction.noveldeglace.viewModel.INovelFragmentPresenter
import com.lnproduction.noveldeglace.viewModel.NovelsFragmentPresenter




class NovelsFragment : BaseFragment(), INovelsFragment, NovelAdapter.ContentListener {

    companion object {
        private val TAG = "NovelsFragment"
        private val KEY_LAYOUT_MANAGER = "layoutManager"
        private val SPAN_COUNT = 2
        private var animShow: Animation? = null
        private var animHide: Animation? = null
    }

    val presenter : INovelFragmentPresenter = NovelsFragmentPresenter(this)

    private lateinit var currentLayoutManagerType: NovelsFragment.LayoutManagerType
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var fab : FloatingActionButton
    private lateinit var optionLayout : RelativeLayout
    private lateinit var optionArrow : ImageView
    private lateinit var novelAdapter : NovelAdapter

    enum class LayoutManagerType { GRID_LAYOUT_MANAGER, LINEAR_LAYOUT_MANAGER }

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
        val rootView = inflater.inflate(R.layout.content_main, container, false)
        activity?.setTitle("Romans")
        recyclerView = rootView.findViewById(R.id.recycler_view_notice_list)
        fab = rootView.findViewById(R.id.fab)
        optionLayout = rootView.findViewById(R.id.option_layout)
        optionArrow = rootView.findViewById(R.id.option_arrow)
        val mMultiLineRadioGroup = rootView.findViewById(R.id.main_activity_multi_line_radio_group) as MultiLineRadioGroup

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        layoutManager = LinearLayoutManager(activity)

        currentLayoutManagerType = NovelsFragment.LayoutManagerType.GRID_LAYOUT_MANAGER

        setRecyclerViewLayoutManager(currentLayoutManagerType)

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            currentLayoutManagerType = savedInstanceState
                    .getSerializable(NovelsFragment.KEY_LAYOUT_MANAGER) as NovelsFragment.LayoutManagerType
        }


        mMultiLineRadioGroup.setOnCheckedChangeListener(object : MultiLineRadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: ViewGroup, button: RadioButton) {
                val rdbGroup = group as MultiLineRadioGroup
                when {
                    rdbGroup.checkedRadioButtonIndex == 0 -> presenter.getNovelList(-1)
                    rdbGroup.checkedRadioButtonIndex == 1 -> presenter.getNovelList(73)
                    rdbGroup.checkedRadioButtonIndex == 2 -> presenter.getNovelList(74)
                    rdbGroup.checkedRadioButtonIndex == 3 -> presenter.getNovelList(265)
                    rdbGroup.checkedRadioButtonIndex == 4 -> presenter.getNovelList(-2)
                }
            }
        })

        animShow = AnimationUtils.loadAnimation( activity, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation( activity, R.anim.view_hide);

        optionLayout.setOnClickListener{
            if(mMultiLineRadioGroup.visibility == View.GONE) {
                mMultiLineRadioGroup.setVisibility(View.VISIBLE);
                optionArrow.setImageResource(R.drawable.chevron_up)
            }else{
                mMultiLineRadioGroup.setVisibility(View.GONE);
                optionArrow.setImageResource(R.drawable.chevron_down)
            }
        }

        fab.hide()

        presenter.onCreate()
        return rootView
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    private fun setRecyclerViewLayoutManager(layoutManagerType: NovelsFragment.LayoutManagerType) {
        var scrollPosition = 0

        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.layoutManager != null) {
            scrollPosition = (recyclerView.layoutManager as LinearLayoutManager)
                    .findFirstCompletelyVisibleItemPosition()
        }

        when (layoutManagerType) {
            NovelsFragment.LayoutManagerType.GRID_LAYOUT_MANAGER -> {
                layoutManager = AutoFitGridLayoutManager(activity, 500)
                currentLayoutManagerType = NovelsFragment.LayoutManagerType.GRID_LAYOUT_MANAGER
            }
            NovelsFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER -> {
                layoutManager = LinearLayoutManager(activity)
                currentLayoutManagerType = NovelsFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER
            }
        }

        with(recyclerView) {
            layoutManager = this@NovelsFragment.layoutManager
            scrollToPosition(scrollPosition)
            val itemDecoration = RecyclerItemDecoration(context, R.dimen.item_offset)
            addItemDecoration(itemDecoration)
        }

    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {

        // Save currently selected layout manager.
        savedInstanceState.putSerializable(NovelsFragment.KEY_LAYOUT_MANAGER, currentLayoutManagerType)
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun getErrorMessage(errorMessage: String) {

    }

    override fun getNovels(novelLists: ArrayList<Novel>?) {

        novelAdapter = NovelAdapter(novelLists,this)
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(novelLists!!.size);
        recyclerView.adapter = novelAdapter
    }

    override fun onItemClicked(item: Novel) {
        val novelFragment = NovelFragment.newInstance(item)
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.sample_content_fragment,novelFragment).addToBackStack("novel").commit()
    }
}