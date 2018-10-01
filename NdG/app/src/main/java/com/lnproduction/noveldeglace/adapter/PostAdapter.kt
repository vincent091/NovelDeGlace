package com.lnproduction.noveldeglace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.model.Post
import com.lnproduction.noveldeglace.utils.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class PostAdapter(private val dataSet: ArrayList<Post>?) :
        RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private var contactListFiltered: List<Post> = dataSet!!
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val txtDate: TextView
        val txtTitle : TextView
        val postImg : SimpleDraweeView

        init {
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener { Log.d(TAG, "Element $adapterPosition clicked.") }
            txtDate = v.findViewById(R.id.date_novel)
            txtTitle = v.findViewById(R.id.title_novel)
            postImg = v.findViewById(R.id.picture_novel)
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
        val post : Post = contactListFiltered.get(position)
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.txtDate.text = parseDateToddMMyyyy(post.dateGMT)
        viewHolder.txtTitle.text = post.postTitle.titleName
        viewHolder.postImg.setImageURI(post.postImg)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = contactListFiltered.size

    companion object {
        private val TAG = "CustomAdapter"
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override protected fun performFiltering(charSequence: CharSequence): FilterResults {
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

            override protected fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                contactListFiltered = filterResults.values as ArrayList<Post>
                notifyDataSetChanged()
            }
        }
    }

    fun parseDateToddMMyyyy(time: String): String? {
        val inputPattern = "yyyy-MM-dd'T'HH:mm:ss"
        val outputPattern = "EEE dd MMMM yyyy HH:mm"
        val inputFormat = SimpleDateFormat(inputPattern, Locale.FRANCE)
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val outputFormat = SimpleDateFormat(outputPattern, Locale.FRANCE)

        var date: Date? = null
        var str: String? = null

        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return str
    }
}