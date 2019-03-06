package com.lnproduction.noveldeglace.ui.NovelList

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lnproduction.noveldeglace.model.Novel
import com.lnproduction.noveldeglace.ui.view.NovelView
import com.lnproduction.noveldeglace.utils.Log
import java.util.*


class NovelAdapter(dataSet: ArrayList<Novel>?,  private val listener: ContentListener) :
        RecyclerView.Adapter<NovelAdapter.ViewHolder>() {

    companion object {
        private const val TAG = "CustomAdapter"
    }

    private var contactNovelFiltered: List<Novel> = dataSet!!

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    class ViewHolder(view: NovelView) : RecyclerView.ViewHolder(view) {
        val novelView : NovelView = view
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = contactNovelFiltered.size

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view.
        val itemView = NovelView(viewGroup.context)

        return ViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.d(TAG, "Element $position set.")
        val novel : Novel = contactNovelFiltered[position]
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.novelView.setNovelList(novel)

        viewHolder.novelView.setOnClickListener {
            listener.onItemClicked(novel)
        }

    }

    interface ContentListener {
        fun onItemClicked(item: Novel)
    }
}