// Main Fragment for showing title screen of app. Features a slideshow of albums.
package psb.app4.thebeatles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class MainFragment : Fragment() {

    companion object
    {
        private var instance : MainFragment? = null

        public fun getInstance() : MainFragment
        {
            return instance!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var slideshow = SlideShow()
        slideshow.start()
    }

}

class SlideShow : Thread() {
    private var images = arrayListOf("intro1", "pleasepleaseme", "with_the_beatles", "harddaysnight",
        "beatlesforsale", "help", "rubber_soul", "revolver", "sgt_pepper", "white", "yellowsubmarine",
        "abbeyroad", "letitbe")
    override fun run() {
        var slideshowhandler : SlideShowHandler
        while(true) {
            for(i in images.indices) {
                slideshowhandler = SlideShowHandler(images[i])
                MainActivity.getInstance().runOnUiThread(slideshowhandler)
                Thread.sleep(3*1000)
            }
        }
    }
}

class SlideShowHandler(private var name: String) : Runnable {
    override fun run() {
        var id = MainActivity.getInstance().resources.getIdentifier(name,"drawable",MainActivity.getInstance().packageName)
        MainActivity.getInstance().findViewById<ImageView>(R.id.titleImage)?.setImageResource(id)
    }
}