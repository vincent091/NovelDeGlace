package com.lnproduction.noveldeglace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.model.Post
import com.lnproduction.noveldeglace.utils.Log

class PostAdapter(private val dataSet: ArrayList<Post>?) :
        RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val txtDate: TextView
        val txtTitle : TextView

        init {
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener { Log.d(TAG, "Element $adapterPosition clicked.") }
            txtDate = v.findViewById(R.id.date_novel)
            txtTitle = v.findViewById(R.id.title_novel)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view.
        val v = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.list_post, viewGroup, false)

        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.d(TAG, "Element $position set.")

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.txtDate.text = dataSet?.get(position)?.dateGMT
        viewHolder.txtTitle.text = dataSet?.get(position)?.postTitle?.titleName
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = dataSet?.size!!

    companion object {
        private val TAG = "CustomAdapter"
    }
}