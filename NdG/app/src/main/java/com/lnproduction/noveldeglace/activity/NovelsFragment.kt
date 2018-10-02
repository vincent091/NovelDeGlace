package com.lnproduction.noveldeglace.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Spinner
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




class NovelsFragment : BaseFragment(), INovelsFragment {
    companion object {
        private val TAG = "NovelsFragment"
        private val KEY_LAYOUT_MANAGER = "layoutManager"
        private val SPAN_COUNT = 2
    }

    val presenter : INovelFragmentPresenter = NovelsFragmentPresenter(this)

    private lateinit var currentLayoutManagerType: NovelsFragment.LayoutManagerType
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var fab : FloatingActionButton
    private lateinit var android_material_design_spinner : Spinner
    private lateinit var novelAdapter : NovelAdapter

    enum class LayoutManagerType { GRID_LAYOUT_MANAGER, LINEAR_LAYOUT_MANAGER }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.content_main, container, false)

        recyclerView = rootView.findViewById(R.id.recycler_view_notice_list)
        fab = rootView.findViewById(R.id.fab)

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        layoutManager = LinearLayoutManager(activity)

        currentLayoutManagerType = NovelsFragment.LayoutManagerType.GRID_LAYOUT_MANAGER

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            currentLayoutManagerType = savedInstanceState
                    .getSerializable(NovelsFragment.KEY_LAYOUT_MANAGER) as NovelsFragment.LayoutManagerType
        }

        val mMultiLineRadioGroup = rootView.findViewById(R.id.main_activity_multi_line_radio_group) as MultiLineRadioGroup

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
        setRecyclerViewLayoutManager(currentLayoutManagerType)
        novelAdapter = NovelAdapter(novelLists)
        recyclerView.adapter = novelAdapter
    }
}