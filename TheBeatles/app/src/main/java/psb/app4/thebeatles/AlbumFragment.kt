//Fragment for displaying list of albums. Contains data classes. Uses RecycleAdapter for display of CardViews
package psb.app4.thebeatles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_album.*
import java.io.*


class AlbumFragment : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    companion object{
        private var instance: AlbumFragment? = null
        public fun getInstance(): AlbumFragment {
            return instance!!
        }

        public var myAlbums = ArrayList<Album>()
        public fun generateAlbums() {
            var is1 = MainActivity.getInstance().getAssets().open("albums.txt")
            var reader1 = BufferedReader(InputStreamReader(is1))
            var lines1 = reader1.readLines()
            var arrayLines1 = lines1.toTypedArray()
            var allData1 = arrayOf<Array<String>>()
            for (i in 0..arrayLines1.size - 1) {
                var array1 = arrayLines1[i].split("^")
                allData1 += array1.toTypedArray()
            }
            reader1.close()

            var temp1: String
            var temp2: String
            var temp3: String
            var temp4: String
            for (i in allData1.indices) {
                temp1 = allData1[i][0]
                temp2 = allData1[i][1]
                temp3 = allData1[i][2]
                temp4 = allData1[i][3]
                var newAlbum = Album(temp1,temp2,temp3,temp4)
                newAlbum.setSongs()
                myAlbums.add(newAlbum)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        generateAlbums()
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instance = this
        layoutManager = LinearLayoutManager(MainActivity.getInstance())
        recycler_view.layoutManager = layoutManager
        adapter = RecyclerAdapter()
        recycler_view.adapter = adapter
    }

}

class Album(private var name : String, private var producer : String, private var date : String, private var image : String) {
    var songListing: ArrayList<Song> = arrayListOf()

    fun getName(): String {
        return this.name
    }
    fun getProducer(): String {
        return this.producer
    }
    fun getDate(): String {
        return this.date
    }
    fun getImage(): String {
        return this.image
    }
    fun setSongs() {
        var is1 = MainActivity.getInstance().getAssets().open(image + ".txt")
        var reader1 = BufferedReader(InputStreamReader(is1))
        var lines1 = reader1.readLines()
        var arrayLines1 = lines1.toTypedArray()
        var allData1 = arrayOf<Array<String>>()
        for (i in 0..arrayLines1.size - 1) {
            var array1 = arrayLines1[i].split("^")
            allData1 += array1.toTypedArray()
        }
        reader1.close()

        var temp1: String
        var temp2: String
        for (i in allData1.indices) {
            temp1 = allData1[i][0]
            temp2 = allData1[i][1]
            var tempSong = Song(temp1, temp2)
            songListing.add(tempSong)
        }
    }
}
class Song(private var name: String, private var composer: String) {
    fun getName(): String {
        return this.name
    }
    fun getComposer(): String {
        return this.composer
    }
}