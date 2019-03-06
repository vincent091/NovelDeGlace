package com.lnproduction.noveldeglace.ui.view

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.jakewharton.picasso.OkHttp3Downloader
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.model.Novel
import com.lnproduction.noveldeglace.utils.PaletteTransformation
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_post.view.*
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


class NovelView : CardView{

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    {
        init()
    }


    private fun init() {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.grid_post, this, true)
    }

    fun setNovelList(novel: Novel){

        val okHttpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()


        val picasso = Picasso.Builder(context)
                .downloader(OkHttp3Downloader(okHttpClient))
                .listener { _, _, e -> e.printStackTrace() }
                .build()

        picasso.load(novel.imgNovel)
                .fit().centerCrop()
                .transform(PaletteTransformation.instance())
                .into(picture_novel, object : Callback.EmptyCallback() {
                    override fun onSuccess() {
                        val bitmap = (picture_novel.drawable as BitmapDrawable).bitmap // Ew!
                        val palette = PaletteTransformation.getPalette(bitmap)
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

                })
    }
}