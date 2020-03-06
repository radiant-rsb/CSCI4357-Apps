//RecycleAdapter for AlbumFragment
package psb.app4.thebeatles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return AlbumFragment.myAlbums.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = AlbumFragment.myAlbums[position].getName()
        holder.itemDetail.text = AlbumFragment.myAlbums[position].getDate()
        holder.itemImage.setImageResource(MainActivity.getInstance().resources.getIdentifier(AlbumFragment.myAlbums[position].getImage(),"drawable",
            MainActivity.getInstance().packageName))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init {
            itemImage = itemView.findViewById(R.id.imageView)
            itemTitle = itemView.findViewById(R.id.name)
            itemDetail = itemView.findViewById(R.id.date)

            var handler = Handler()
            itemView.setOnClickListener(handler)

        }

        inner class Handler() : View.OnClickListener {
            override fun onClick(v: View?) {
                val itemPosition = getLayoutPosition()
                //Get the navigation controller
                var navController = Navigation.findNavController(AlbumFragment.getInstance().view!!)
                val bundle = Bundle()
                bundle.putInt("position", itemPosition)
                navController.navigate(R.id.albumToSong, bundle)
            }
        }
    }
}