package com.lnproduction.noveldeglace.ui.view

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.model.Post
import com.lnproduction.noveldeglace.utils.PaletteTransformation
import com.lnproduction.noveldeglace.utils.parseDateToddMMyyyy
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_post.view.*


class PostView : CardView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    {
        init()
    }


    private fun init() {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.list_post, this, true)
    }

     fun setPostList(post: Post){
         date_novel.text = parseDateToddMMyyyy(post.dateGMT)
         title_novel.text = post.postTitle.titleName

         Picasso.with(context)
                 .load(post.postImg)
                 .fit().centerCrop()
                 .transform(PaletteTransformation.instance())
                 .into(picture_novel, object : Callback.EmptyCallback() {
                     override fun onSuccess() {
                         val bitmap = (picture_novel.drawable as BitmapDrawable).bitmap // Ew!
                         val palette = PaletteTransformation.getPalette(bitmap)
                         Log.e("Palette",palette.darkMutedSwatch?.rgb.toString())
                         if(palette.darkMutedSwatch!=null) {
                             date_novel.setTextColor(palette.darkMutedSwatch!!.rgb)
                             title_novel.setTextColor(palette.darkMutedSwatch!!.rgb)
                         }else{
                             date_novel.setTextColor(palette.getDominantColor(0))
                             title_novel.setTextColor(palette.getDominantColor(0))
                         }
                         if(palette.lightMutedSwatch!=null){
                             card_view_top.setBackgroundColor(palette.lightMutedSwatch!!.rgb)
                         }else{
                             card_view_top.setBackgroundColor(palette.getLightMutedColor(0))
                         }
                     }
                 })
    }

}