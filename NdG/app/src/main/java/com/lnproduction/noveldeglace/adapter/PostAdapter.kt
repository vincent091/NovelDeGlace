package com.lnproduction.noveldeglace.adapter

import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.facebook.common.executors.UiThreadImmediateExecutorService
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequest
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.model.Post
import com.lnproduction.noveldeglace.utils.Log
import com.lnproduction.noveldeglace.utils.parseDateToddMMyyyy
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
        val cardView : CardView

        init {
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener { Log.d(TAG, "Element $adapterPosition clicked.") }
            txtDate = v.findViewById(R.id.date_novel)
            txtTitle = v.findViewById(R.id.title_novel)
            postImg = v.findViewById(R.id.picture_novel)
            cardView = v.findViewById(R.id.card_view_top)
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

        val imagePipeline = Fresco.getImagePipeline()
        val imageRequest = ImageRequest.fromUri(Uri.parse(post.postImg))
        val dataSource = imagePipeline.fetchDecodedImage(imageRequest, null)
        dataSource.subscribe(object : BaseBitmapDataSubscriber() {

            override fun onNewResultImpl(bitmap: Bitmap?) {
                if (bitmap != null) {
                    Palette.from(bitmap).generate { palette ->
                        val vibrantSwatch = palette?.lightMutedSwatch
                        val darkvibrantSwatch = palette?.darkMutedSwatch
                        if (vibrantSwatch != null && darkvibrantSwatch != null) {
                            viewHolder.cardView.setBackgroundColor(vibrantSwatch.rgb)
                            viewHolder.txtDate.setTextColor(darkvibrantSwatch.rgb)
                            viewHolder.txtTitle.setTextColor(darkvibrantSwatch.rgb)
                        }
                    }
                }
            }

            override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>) {

            }
        }, UiThreadImmediateExecutorService.getInstance())
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