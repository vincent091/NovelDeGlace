package com.lnproduction.noveldeglace.ui.view

import android.content.Context
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.hendraanggrian.pikasso.palette.palette
import com.hendraanggrian.pikasso.picasso
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.model.Post
import com.lnproduction.noveldeglace.utils.PaletteTransformation
import com.lnproduction.noveldeglace.utils.parseDateToddMMyyyy
import kotlinx.android.synthetic.main.list_post.view.*


class PostView(context: Context) : CardView(context) {

    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.list_post, this, true)
    }

    fun setPostList(post: Post) {
        date_novel.text = parseDateToddMMyyyy(post.dateGMT)
        title_novel.text = post.postTitle.titleName
        picasso.load(post.postImg)
                .fit()
                .transform(PaletteTransformation.instance())
                .palette(picture_novel){
                    onSuccess {
                        val palette = this.palette
                        if (palette.darkMutedSwatch != null) {
                            date_novel.setTextColor(palette.darkMutedSwatch!!.rgb)
                            title_novel.setTextColor(palette.darkMutedSwatch!!.rgb)
                        } else {
                            date_novel.setTextColor(palette.getDominantColor(0))
                            title_novel.setTextColor(palette.getDominantColor(0))
                        }
                        if (palette.lightMutedSwatch != null) {
                            card_view_top.setBackgroundColor(palette.lightMutedSwatch!!.rgb)
                        } else {
                            card_view_top.setBackgroundColor(palette.getLightMutedColor(0))
                        }
                    }
                }

    }

}