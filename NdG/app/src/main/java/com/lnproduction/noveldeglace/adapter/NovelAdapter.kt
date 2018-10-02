package com.lnproduction.noveldeglace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.model.Novel
import com.lnproduction.noveldeglace.utils.Log
import java.util.*

class NovelAdapter(private val dataSet: ArrayList<Novel>?) :
        RecyclerView.Adapter<NovelAdapter.ViewHolder>() {

    companion object {
        private val TAG = "CustomAdapter"
    }

    private var contactNovelFiltered: List<Novel> = dataSet!!

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val postImg : SimpleDraweeView

        init {
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener { Log.d(NovelAdapter.TAG, "Element $adapterPosition clicked.") }
            postImg = v.findViewById(R.id.picture_novel)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = contactNovelFiltered.size

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NovelAdapter.ViewHolder {
        // Create a new view.
        val v = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.grid_post, viewGroup, false)

        return NovelAdapter.ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: NovelAdapter.ViewHolder, position: Int) {
        Log.d(NovelAdapter.TAG, "Element $position set.")
        val novel : Novel = contactNovelFiltered.get(position)
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.postImg.setImageURI(novel.imgNovel.split("?")[0])

    }
}