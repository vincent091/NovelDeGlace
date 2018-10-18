package com.lnproduction.noveldeglace.view

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.model.Post
import com.lnproduction.noveldeglace.utils.PaletteTransformation
import com.lnproduction.noveldeglace.utils.parseDateToddMMyyyy
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso




class PostView : CardView {

    private lateinit var txtDate: TextView
    private lateinit var txtTitle : TextView
    private lateinit var postImg : ImageView
    private lateinit var cardView : CardView

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    {
        init()
    }


    fun init() {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.list_post, this, true)
        txtDate = findViewById(R.id.date_novel) as TextView
        txtTitle = findViewById(R.id.title_novel)as TextView
        postImg = findViewById(R.id.picture_novel)as ImageView
        cardView = findViewById(R.id.card_view_top)as CardView
    }

     fun setNovel(post: Post){
         txtDate.text = parseDateToddMMyyyy(post.dateGMT)
         txtTitle.text = post.postTitle.titleName

         Picasso.with(postImg.context)
                 .load(post.postImg)
                 .fit().centerCrop()
                 .transform(PaletteTransformation.instance())
                 .into(postImg, object : Callback.EmptyCallback() {
                     override fun onSuccess() {
                         val bitmap = (postImg.getDrawable() as BitmapDrawable).bitmap // Ew!
                         val palette = PaletteTransformation.getPalette(bitmap)
                         cardView.setBackgroundColor(palette.lightMutedSwatch!!.rgb)
                         txtDate.setTextColor(palette.darkMutedSwatch!!.rgb)
                         txtTitle.setTextColor(palette.darkMutedSwatch!!.rgb)
                     }
                 })
    }

}