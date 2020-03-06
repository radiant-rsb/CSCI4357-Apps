// RecycleAdapter for SongFragment. This displays cards for songs.
package psb.app4.thebeatles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapterSong : RecyclerView.Adapter<RecyclerAdapterSong.ViewHolder> {
    private var pos: Int = -1

    constructor(pos: Int) : this() {
        this.pos = pos
    }
    constructor() {}
    public fun setPos(pos: Int) {
        this.pos = pos
    }


    override fun onBindViewHolder(holder: RecyclerAdapterSong.ViewHolder, position: Int) {
        holder.itemSong.text = AlbumFragment.myAlbums[pos].songListing[position].getName()
        holder.itemComposer.text = AlbumFragment.myAlbums[pos].songListing[position].getComposer()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterSong.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.song_card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return AlbumFragment.myAlbums[pos].songListing.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemSong: TextView
        var itemComposer: TextView

        init {
            itemSong = itemView.findViewById(R.id.song)
            itemComposer = itemView.findViewById(R.id.composer)
            var handler = Handler()
            itemView.setOnClickListener(handler)

        }

        inner class Handler() : View.OnClickListener {
            override fun onClick(v: View?) {
                val itemPosition: Int = getLayoutPosition()
                var songNew = AlbumFragment.myAlbums[pos].songListing[itemPosition].getName()
                VideoFragment.set(songNew)
                val navController = Navigation.findNavController(MainActivity.getInstance(), R.id.fragment)
                if (navController.currentDestination?.id == R.id.songFragment) // Guards against unhandled exceptions with finding songToVideo.
                    navController.navigate(R.id.songToVideo,null)
            }
        }
    }
}