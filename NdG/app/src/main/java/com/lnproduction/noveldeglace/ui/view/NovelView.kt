package com.lnproduction.noveldeglace.ui.view

import android.content.Context
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.hendraanggrian.pikasso.palette.palette
import com.hendraanggrian.pikasso.picasso
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.model.Novel
import com.lnproduction.noveldeglace.utils.PaletteTransformation
import kotlinx.android.synthetic.main.list_post.view.*


class NovelView(context: Context) : CardView(context){

    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.grid_post, this, true)
    }

    fun setNovelList(novel: Novel){

        picasso.load(novel.imgNovel)
                .fit()
                .transform(PaletteTransformation.instance())
                .palette(picture_novel){
                    onSuccess {
                        val palette = this.palette
                        if(palette.darkMutedSwatch!=null) {
                            novel.textColor = palette.darkMutedSwatch!!.rgb
                        }else{
                            novel.textColor = palette.getDominantColor(0)
                        }
                        if(palette.lightMutedSwatch!=null){
                            novel.backgroundColor = palette.lightMutedSwatch!!.rgb
                        }else{
                            novel.backgroundColor = palette.getLightMutedColor(0)
                        }
                    }
                }

    }
}