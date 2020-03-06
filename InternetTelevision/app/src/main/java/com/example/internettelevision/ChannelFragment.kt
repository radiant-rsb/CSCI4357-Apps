// Channel screen for selecting and playing videos.

package com.example.internettelevision

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.webkit.*
import android.widget.Button
import android.widget.Space
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_channel.*


class ChannelFragment : Fragment() {

    companion object {
        private var instance: ChannelFragment? = null
        public var channels = ArrayList<Station>()

        public fun getInstance(): ChannelFragment {
            return instance!!
        }
        public fun generateChannels() {
            channels.add(Station("France24","http://static.france24.com/live/F24_EN_LO_HLS/live_web.m3u8"))
            channels.add(Station("Weather","http://weather-lh.akamaihd.net/i/twc_1@92006/master.m3u8"))
            channels.add(Station("CBS","http://cbsnewshd-lh.akamaihd.net/i/CBSNHD_7@199302/master.m3u8"))
            channels.add(Station("Free Speech","https://edge.free-speech-tv-live.top.comcast.net/out/u/fstv.m3u8"))
            channels.add(Station("Travel","http://media4.tripsmarter.com:1935/LiveTV/ACVBHD/chucklist.m3u8"))
            channels.add(Station("SECDN","http://na-all15.secdn.net/pegstream3-live/play/c3e1e4c4-7f11-4a54-8b8f-c590a95b4ade/playlist.m3u8"))
            channels.add(Station("Daytona","http://oflash.dfw.swagit.com/live/daytonabeachfl/smil:std-4x3-1-a/chucklist.m3u8"))
            channels.add(Station("HouseTV","http://d3ktuc8v2sjk6m.cloudfront.net/livetv/ngrp:HouseChannel_all/chucklist.m3u8"))
            channels.add(Station("ROKU","http://173.199.158.79:1935/roku/myStream/playlist.m3u8"))
            channels.add(Station("Orange County","http://otv3.ocfl.net:1936/OrangeTV/smil:OrangeTV.smil/chunklist_w1007974604_b894100_sleng.m3u8"))
            channels.add(Station("Seminole County","http://video.seminolecountyfl.gov:1935/live/SGTV/chunklist.m3u8"))
            channels.add(Station("University of Texas","http://tstv-stream.tsm.utexas.edu/hls/livestream_hi/index.m3u8"))
            channels.add(Station("University of California","http://diffusionm4.assnat.qc.ca/canal9/250.sdp/playlist.m3u8"))
        }
        public fun addStation(name: String, url: String){
            channels.add(Station(name,url))
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        instance = this
// Inflate the layout for this fragment
        generateChannels()
        return inflater.inflate(R.layout.fragment_channel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var handlerbutton = Handler1()
        //generateChannels()

        var space: Space
        for (i in channels) {
            var button1 = inflate(MainActivity.getInstance(), R.layout.button, null) as Button

            var text = i.getLetters()
            button1.setText(text)

            button1.setOnClickListener(handlerbutton)

            linearLayout.addView(button1)

            space = Space(MainActivity.getInstance())
            space.minimumHeight = 15
            linearLayout.addView(space)
        }
    }

}

class Handler1 : View.OnClickListener {
    override fun onClick(view: View?) {
        var button: Button
        var text: String
        if (view is Button) {
            button = view
            text = button.text.toString()

            for (i in ChannelFragment.channels) {
                if (i.getLetters() == text) {

                    var url = i.getUrl()
                    VideoFragment.set(url)
                    val navController = Navigation.findNavController(MainActivity.getInstance(), R.id.fragment)
                    if (navController.currentDestination?.id == R.id.channelFragment) // Guards against unhandled exceptions with finding channelToVideo.
                        navController.navigate(R.id.channelToVideo,null)
                }
            }
        }
    }
}
// Simple class for storing data
class Station(private var letters: String, private var url: String) {
    fun getLetters(): String {
        return this.letters
    }

    fun getUrl(): String {
        return this.url
    }
}