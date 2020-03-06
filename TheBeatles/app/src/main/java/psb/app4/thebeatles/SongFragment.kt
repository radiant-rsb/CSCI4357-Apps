//Fragment for displaying track listings for selected album. Selecting a cell goes to VideoFragment
package psb.app4.thebeatles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_song.*


private var layoutManager: RecyclerView.LayoutManager? = null
private var adapter: RecyclerView.Adapter<RecyclerAdapterSong.ViewHolder>? = null

class SongFragment : Fragment()
{
    companion object
    {
        private var instance : SongFragment? = null
        var currentPos: Int = 0

        public fun getInstance() : SongFragment
        {
            return instance!!
        }
        public fun setPos(pos: Int) {
            currentPos = pos
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        var arguments = this.getArguments()
        var pos = arguments?.getInt("position")

        layoutManager = LinearLayoutManager(MainActivity.getInstance())
        recycler_view2.layoutManager = layoutManager
        adapter = RecyclerAdapterSong(pos!!) //this is the desired contact selected
        setPos(pos!!) //saved for going back to song fragment
        recycler_view2.adapter = adapter
    }


}
