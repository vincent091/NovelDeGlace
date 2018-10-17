package com.lnproduction.noveldeglace.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import com.lnproduction.noveldeglace.R
import kotlinx.android.synthetic.main.webview_activity.*
import org.jsoup.Jsoup
import java.io.IOException
import java.util.*


class WebViewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview_activity)
        initWebView()

        Thread(Runnable {
            val builder = StringBuilder()

            try {
                val doc = Jsoup.connect(intent.getStringExtra("chapter_url")).get()
                val divText = doc.select("div.entry-content-chapitre.chapter-inner.chapter-content").select("p")
                for(element in divText){
                    if(element.hasText())
                        builder.append(element)
                    if(element.select("img").hasAttr("class")){
                        builder.append("<p><img src=\"")
                                .append(element.select("img").attr("src").split("?")[0])
                                .append(";\"/></p>")
                    }

                }

            } catch (e: IOException) {
                builder.append("Error : ").append(e.message).append("n")
            }


            val justifyTag = "<html><body style='text-align:justify;'>%s</body></html>"
            val dataString = String.format(Locale.getDefault(), justifyTag, builder.toString())

            runOnUiThread{
                content_webview.loadDataWithBaseURL("", dataString, "text/html", "UTF-8", "");
            }

        }).start()

    }

    @SuppressLint("SetJavaScriptEnabled")
    fun initWebView(){
        val settings = content_webview.getSettings()
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.setSupportZoom(true)
        val res = resources
        val fontSize = res.getDimension(R.dimen.txtSize)
        settings.defaultFontSize = fontSize.toInt()
        settings.builtInZoomControls = false
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        settings.domStorageEnabled = true
        content_webview.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        content_webview.isScrollbarFadingEnabled = true
        content_webview.setLayerType(View.LAYER_TYPE_HARDWARE, null)

    }

}