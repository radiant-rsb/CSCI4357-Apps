/**
 * Preston Bennett
 * App 4: The Beatles Youtube Player
 * Dr. Mark Smith | CSCI 4357 Programming Mobile Devices
 *
 * This app uses Youtube's API, JSON Processing, CardViews, Text Reading, and RecycleAdapters to create
 * an interactive discography of The Beatles, including all of the band's studio albums and
 * tracks from each album. Not every track results in a video being played due to the rules
 * for best video, but many of the most popular songs are picked and played in VideoFragment's
 * WebView widget. Sometimes, a cover video will be played instead.
 *
 * Please forward questions and concerns to pbennett2@cub.uca.edu
 */


// MainActivity
package psb.app4.thebeatles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object
    {
        private var instance : MainActivity? = null

        public fun getInstance() : MainActivity
        {
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        instance = this
        val navController = Navigation.findNavController(this, R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)

    }
}
