package com.lnproduction.noveldeglace.ui.NovelList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.base.BaseFragment
import com.lnproduction.noveldeglace.model.Novel
import com.lnproduction.noveldeglace.ui.NovelDetails.NovelFragment
import com.lnproduction.noveldeglace.utils.AutoFitGridLayoutManager
import com.lnproduction.noveldeglace.utils.MultiLineRadioGroup
import com.lnproduction.noveldeglace.utils.RecyclerItemDecoration
import kotlinx.android.synthetic.main.content_main.*


class NovelsFragment : BaseFragment(), INovelsFragment, NovelAdapter.ContentListener {

    companion object {
        private val TAG = "NovelsFragment"
        private val KEY_LAYOUT_MANAGER = "layoutManager"
        private var animShow: Animation? = null
        private var animHide: Animation? = null
    }

    val presenter = NovelsFragmentPresenter()

    private lateinit var currentLayoutManagerType: LayoutManagerType
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var novelAdapter : NovelAdapter

    enum class LayoutManagerType { GRID_LAYOUT_MANAGER, LINEAR_LAYOUT_MANAGER }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val menuItem = menu.findItem(R.id.action_search)
        menuItem.setVisible(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.content_main, container, false)
        activity?.setTitle("Romans")

        presenter.createView(this)
        presenter.getNovelList(-1)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(activity)

        currentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER

        setRecyclerViewLayoutManager(currentLayoutManagerType)

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            currentLayoutManagerType = savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER) as LayoutManagerType
        }


        main_activity_multi_line_radio_group.setOnCheckedChangeListener(object : MultiLineRadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: ViewGroup, button: RadioButton) {
                val rdbGroup = group as MultiLineRadioGroup
                presenter.filterByRadioButton(rdbGroup.checkedRadioButtonIndex)
            }
        })

        animShow = AnimationUtils.loadAnimation( activity, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation( activity, R.anim.view_hide);

        option_layout.setOnClickListener{
            if(main_activity_multi_line_radio_group.visibility == View.GONE) {
                main_activity_multi_line_radio_group.setVisibility(View.VISIBLE);
                option_arrow.setImageResource(R.drawable.chevron_up)
            }else{
                main_activity_multi_line_radio_group.setVisibility(View.GONE);
                option_arrow.setImageResource(R.drawable.chevron_down)
            }
        }

        fab.hide()

    }

    override fun onDestroyView() {
        presenter.destroyView()
        super.onDestroyView()
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    private fun setRecyclerViewLayoutManager(layoutManagerType: LayoutManagerType) {
        var scrollPosition = 0

        // If a layout manager has already been set, get current scroll position.
        if (recycler_view_notice_list.layoutManager != null) {
            scrollPosition = (recycler_view_notice_list.layoutManager as LinearLayoutManager)
                    .findFirstCompletelyVisibleItemPosition()
        }

        when (layoutManagerType) {
            LayoutManagerType.GRID_LAYOUT_MANAGER -> {
                layoutManager = AutoFitGridLayoutManager(activity, 500)
                currentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER
            }
            LayoutManagerType.LINEAR_LAYOUT_MANAGER -> {
                layoutManager = LinearLayoutManager(activity)
                currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER
            }
        }

        with(recycler_view_notice_list) {
            layoutManager = this@NovelsFragment.layoutManager
            scrollToPosition(scrollPosition)
            val itemDecoration = RecyclerItemDecoration(context, R.dimen.item_offset)
            addItemDecoration(itemDecoration)
        }

    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {

        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, currentLayoutManagerType)
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun getErrorMessage(errorMessage: String) {

    }

    override fun getNovels(novelLists: ArrayList<Novel>?) {

        novelAdapter = NovelAdapter(novelLists, this)
        recycler_view_notice_list.setHasFixedSize(true);
        recycler_view_notice_list.setItemViewCacheSize(novelLists!!.size);
        recycler_view_notice_list.adapter = novelAdapter
    }

    override fun onItemClicked(item: Novel) {
        val novelFragment = NovelFragment.newInstance(presenter.serializeResponse(item))
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.sample_content_fragment,novelFragment).addToBackStack("novel").commit()
    }
}