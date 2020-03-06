// Main Fragment for playing web videos.

package com.example.internettelevision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_video.*

class VideoFragment : Fragment() {

    companion object {
        private var url: String = "none"
        public fun set(new: String) {
            url = new
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        //ADD CODE HERE
        val settings = webView.getSettings()
        settings.setJavaScriptEnabled(true)
        settings.setDomStorageEnabled(true)
        settings.setMinimumFontSize(10)
        settings.setLoadWithOverviewMode(true)
        settings.setUseWideViewPort(true)
        settings.setBuiltInZoomControls(true)
        settings.setDisplayZoomControls(false)
        webView.setVerticalScrollBarEnabled(false)
        settings.setDomStorageEnabled(true)
        webView.setWebChromeClient(WebChromeClient())
        webView.loadUrl(url)
    }


}