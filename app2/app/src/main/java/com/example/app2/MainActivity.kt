/*
* Preston Bennett
* App 2: Web Radio App
* Dr. Mark Smith | CSCI 4357 Programming Mobile Devices
*
* This app uses widgets and a handler to create an interactive UI and a WebView
* to summon web streams of AM and FM radio stations.
*
* Please forward questions and concerns to pbennett2@cub.uca.edu
* */


package com.example.app2

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.app.AlertDialog
import android.webkit.WebView
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object
    {
        private var instance : MainActivity? = null

        public fun getInstance() : MainActivity
        {
            return instance!!
        }

    }

    //Checks internet connection
    fun checkInternetConnection(): Boolean
    {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // Test for connection
        cm.isDefaultNetworkActive
        val networkInfo = cm.isDefaultNetworkActive
        if (networkInfo)
            return true
        else
            return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null)
            supportActionBar?.hide() //Hide Title Bar
        setContentView(R.layout.activity_main)

        instance = this

        //create handler
        var handler = Handler()

        //if user doesn't have internet connection, create a warning dialog
        var result = checkInternetConnection()
        if (!result) {
            //Display alert box
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Not Connected To Internet")
            dialogBuilder.setPositiveButton("OK",handler)
            dialogBuilder.setNegativeButton("Cancel", handler)
            val alert1 = dialogBuilder.create()
            alert1.setTitle("No Internet")
            alert1.show()

        }

        //Register Handler with UI
        slider.setOnSeekBarChangeListener(handler)
        switch1.setOnCheckedChangeListener(handler)
        button.setOnClickListener(handler)

    }
}

//Handler class with private data for the app.
class Handler : View.OnClickListener, SeekBar.OnSeekBarChangeListener,
    CompoundButton.OnCheckedChangeListener,DialogInterface.OnClickListener
{
    private var am_stations = arrayOf("wabc","wbap","wls","karnam")
    private var fm_stations = arrayOf("wxyt","kurb","wkim","wwtn","kdxe","karnfm","klal")
    private var fm_nums = arrayOf(41,45,47,51,55,61,79)
    private var am_nums = arrayOf(36,40,44,49)
    private var setting = "am"
    private var current = "" //current station. 'wxytfm' 'wabcam'
    private var imageView = MainActivity.getInstance().findViewById<ImageView>(R.id.imageView)
    private var textView = MainActivity.getInstance().findViewById<TextView>(R.id.textView)


    override fun onClick(p0: DialogInterface?, p1: Int) {
        if (p1 == DialogInterface.BUTTON_NEGATIVE)
        { }
        else if (p1 == DialogInterface.BUTTON_POSITIVE)
        { }
    }

    override fun onClick(p0: View?) {
        var wv : WebView = MainActivity.getInstance().findViewById<WebView>(R.id.webView)
        if (current != ""){
            var radio = "http://playerservices.streamtheworld.com/api/livestream-redirect/" +
                    current + setting + ".mp3"
            wv?.loadUrl("http://static.france24.com/live/F24_EN_LO_HLS/live_web.m3u8")
        }

    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        var progress = p1
        if (setting == "am") {
            when (progress) {
                am_nums[0] -> current = am_stations[0]
                am_nums[1] -> current = am_stations[1]
                am_nums[2] -> current = am_stations[2]
                am_nums[3] -> current = am_stations[3]
                else -> current = ""
            }
        }
        else if (setting == "fm"){
            when (progress) {
                fm_nums[0] -> current = fm_stations[0]
                fm_nums[1] -> current = fm_stations[1]
                fm_nums[2] -> current = fm_stations[2]
                fm_nums[3] -> current = fm_stations[3]
                fm_nums[4] -> current = fm_stations[4]
                fm_nums[5] -> current = fm_stations[5]
                fm_nums[6] -> current = fm_stations[6]
                else -> current = ""
            }
        }
        if (current != "") {
            var id = MainActivity.getInstance().resources.getIdentifier(current, "drawable",MainActivity.getInstance().packageName)
            imageView.setImageResource(id)
            textView.setText(current.toUpperCase(Locale.getDefault()))
        }
        else {
            var id = MainActivity.getInstance().resources.getIdentifier("launch", "drawable",MainActivity.getInstance().packageName)
            imageView.setImageResource(id)
            textView.setText("Now Playing...")
        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        var text = p0?.getText()
        if (text == "AM") {
            p0?.setText("FM")
            setting = "fm"
        }
        else {
            p0?.setText("AM")
            setting = "am"
        }
        current = ""
        var id = MainActivity.getInstance().resources.getIdentifier("launch", "drawable",MainActivity.getInstance().packageName)
        imageView.setImageResource(id)
        textView.setText("Now Playing...")
    }


}

