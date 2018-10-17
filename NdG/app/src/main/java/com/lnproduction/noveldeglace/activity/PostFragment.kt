package com.lnproduction.noveldeglace.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.adapter.PostAdapter
import com.lnproduction.noveldeglace.model.Post
import com.lnproduction.noveldeglace.utils.AutoFitGridLayoutManager
import com.lnproduction.noveldeglace.utils.RecyclerItemDecoration
import com.lnproduction.noveldeglace.viewModel.PostFragmentPresenter


class PostFragment : BaseFragment() , IPostFragment {

    private lateinit var currentLayoutManagerType: LayoutManagerType
    private lateinit var recyclerView: RecyclerView
    private lateinit var rltvFilter : RelativeLayout
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var postAdapter : PostAdapter

    enum class LayoutManagerType { GRID_LAYOUT_MANAGER, LINEAR_LAYOUT_MANAGER }

    var presenterFragment = PostFragmentPresenter()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.content_main,
                container, false).apply { tag = TAG}

        activity?.setTitle("Nouveauté")

        recyclerView = rootView.findViewById(R.id.recycler_view_notice_list)
        rltvFilter = rootView.findViewById(R.id.rltvFilter)

        rltvFilter.visibility = View.GONE
        layoutManager = LinearLayoutManager(activity)

        currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER

        if (savedInstanceState != null) {
            currentLayoutManagerType = savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER) as LayoutManagerType
        }

        presenterFragment.createView(this)
        presenterFragment.getPostsList()


        return rootView
    }

    override fun onDestroy() {
        presenterFragment.destroyView()
        super.onDestroy()
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    private fun setRecyclerViewLayoutManager(layoutManagerType: LayoutManagerType) {
        var scrollPosition = 0

        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.layoutManager != null) {
            scrollPosition = (recyclerView.layoutManager as LinearLayoutManager)
                    .findFirstCompletelyVisibleItemPosition()
        }

        when (layoutManagerType) {
            PostFragment.LayoutManagerType.GRID_LAYOUT_MANAGER -> {
                layoutManager = AutoFitGridLayoutManager(activity, 500)
                currentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER
            }
            PostFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER -> {
                layoutManager = LinearLayoutManager(activity)
                currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER
            }
        }

        with(recyclerView) {
            layoutManager = this@PostFragment.layoutManager
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


    companion object {
        private val TAG = "PostFragment"
        private val KEY_LAYOUT_MANAGER = "layoutManager"
    }

    override fun getErrorMessage(errorMessage: String) {
    }

    override fun getPosts(postsList: ArrayList<Post>?) {
        setRecyclerViewLayoutManager(currentLayoutManagerType)
        postAdapter = PostAdapter(postsList)
        recyclerView.adapter = postAdapter
    }

    fun filterTextWithQuery(query: String) {
        postAdapter.getFilter().filter(query)
    }
}