package com.lnproduction.noveldeglace.adapter

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.lnproduction.noveldeglace.model.Post
import com.lnproduction.noveldeglace.view.PostView
import java.util.*


class PostAdapter(private val dataSet: ArrayList<Post>?) :
        RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private var contactListFiltered: List<Post> = dataSet!!
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    class ViewHolder(v: PostView) : RecyclerView.ViewHolder(v) {
        val postView: PostView

        init {
            // Define click listener for the ViewHolder's View.
            postView = v
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view.
        val itemView : PostView = PostView(viewGroup.context)
        return ViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val post : Post = contactListFiltered.get(position)
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.postView.setNovel(post)
        viewHolder.postView.setOnClickListener {
            Toast.makeText(viewHolder.postView.context, "Click in position :"+ position,Toast.LENGTH_SHORT).show()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = contactListFiltered.size

    companion object {
        private val TAG = "CustomAdapter"
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    contactListFiltered = this@PostAdapter.dataSet!!
                } else {
                    val filteredList = ArrayList<Post>()
                    for (row in this@PostAdapter.dataSet!!) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.postTitle.titleName.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }

                    contactListFiltered = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = contactListFiltered
                return filterResults
            }

            @Suppress("Unchecked_cast")
            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                contactListFiltered = filterResults.values as ArrayList<Post>
                notifyDataSetChanged()
            }
        }
    }
}