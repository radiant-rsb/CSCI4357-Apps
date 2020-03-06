//Fragment for displaying video and processing chosen cell. If no credible results come up, return to SongFragment after prompt.
package psb.app4.thebeatles

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_video.*
import org.json.JSONObject
import java.net.URL

class VideoFragment : Fragment() {

    companion object {
        private var songName: String = "none"
        public fun set(new: String) {
            songName = new
        }
        private var instance: VideoFragment? = null
        public fun getInstance(): VideoFragment {
            return instance!!
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
        instance = this
        var song: String = songName
        song = song.replace(" ", "+")
        var origSong = songName

        //Set the artist
        var artist = "The Beatles"
        artist = artist.replace(" ","+")
        var origArtist = "The Beatles"
        //Encode search for YouTube
        val keywords = artist + "+" +  song
        val max = 50

        //Set the youtube search
        //Set the youtube search
        val string = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=$keywords&order=viewCount&maxResults=$max&type=video&videoCategory=Music&key=AIzaSyAJiIwkPJA1F7Xmy_jZobS0KRYA2ystamE"
        var helper = Helper(string, origSong, origArtist)
        var thread = Thread(helper)
        thread.start()

    }
    inner class Helper : Runnable
    {
        private var url : String = ""
        private var song : String = ""
        private var artist : String = ""

        constructor(url : String, song : String, artist : String)
        {
            this.url = url
            this.song = song
            this.artist = artist
        }
        override public fun  run()
        {
            val data = URL(url).readText()
            //println(data)
            var json = JSONObject(data)
            var items = json.getJSONArray("items") // this is the "items: [ ] part
            var titles = ArrayList<String>()
            var videos = ArrayList<String>()
            for (i in 0 until items.length())
            {
                var videoObject = items.getJSONObject(i)
                //val title = videoObject.getString("title")
                //val videoId = videoObject.getString("id")
                println(videoObject)
                var idDict = videoObject.getJSONObject("id")
                println(idDict)
                var videoId = idDict.getString("videoId")
                println(videoId)
                var snippetDict = videoObject.getJSONObject("snippet")
                var title =  snippetDict.getString("title")
                println(title)
                titles.add(title)
                videos.add(videoId)
            }
            for (i in 0 until items.length())
            {
                //Get the ith item
                var videoObject = items.getJSONObject(i)

                //Extracth the id Hashmap
                var idDict = videoObject.getJSONObject("id")

                //Get the videoid using videoId key
                var videoId = idDict.getString("videoId")
                println(videoId)
                //Get the snippet Hashmap
                var snippetDict = videoObject.getJSONObject("snippet")
                //Get the title
                var title =  snippetDict.getString("title")

                //Add the titles to the lists
                titles.add(title)
                videos.add(videoId)
            }

            var selected_video : String = ""
            var selected_title : String = ""

            for (i in titles.indices ) {
                println("target: " + artist + " - " + song)
                println(titles[i])
                if (titles[i] == artist + " - " + song){
                    selected_video = videos[i]
                    selected_title = titles[i]
                    break
                }
            }
            var helper1 = UIThreadHelper(selected_video, selected_title)
            MainActivity.getInstance().runOnUiThread(helper1)


        }
    }

    inner class UIThreadHelper : Runnable
    {
        private var video : String = ""
        private var title : String = ""

        constructor(video : String, title : String)
        {
            this.video = video
            this.title = title
        }
        override  fun run()
        {
            if (video == "" && title == ""){
                //Display alert box
                val dialogBuilder = AlertDialog.Builder(MainActivity.getInstance())
                var handler = Handler()
                dialogBuilder.setMessage("No video found.")
                dialogBuilder.setPositiveButton("OK",handler)
                //dialogBuilder.setNegativeButton("Cancel", handler)
                val alert1 = dialogBuilder.create()
                alert1.setTitle("No Video Found")
                alert1.show()
            }
            else {
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
                var str = "https://www.youtube.com/watch?v=" + video
                webView.loadUrl(str)
            }
        }
    }
}

class Handler: DialogInterface.OnClickListener {
    override fun onClick(p0: DialogInterface?, p1: Int) {
        val itemPosition = SongFragment.currentPos
        //Get the navigation controller
        var navController = Navigation.findNavController(VideoFragment.getInstance().view!!)
        val bundle = Bundle()
        bundle.putInt("position", itemPosition)
        navController.navigate(R.id.videoToSong, bundle)
    }
}