// Configuration Screen

package com.example.internettelevision

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_config.*

class ConfigFragment : Fragment() {

    companion object
    {
        private var instance : ConfigFragment? = null
        public fun getInstance() : ConfigFragment
        {
            return instance!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_config, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var handler = Handler2()
        callLettersPrompt.setOnEditorActionListener(handler)
        urlPrompt.setOnEditorActionListener(handler)
        submit.setOnClickListener(handler)

    }

}

class Handler2 : View.OnClickListener, TextView.OnEditorActionListener {
    override fun onClick(p0: View?) {
        var prompt1 = MainActivity.getInstance().callLettersPrompt
        var prompt2 = MainActivity.getInstance().urlPrompt
        var string1 = prompt1.getText().toString()
        var string2 = prompt2.getText().toString()
        if (string1 != "" && string2 != "") { // Assumes no duplicates or faulty entries.
            ChannelFragment.addStation(string1, string2)
        }

    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean
    {
        var im = MainActivity.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(v?.getWindowToken(),0)
        return true
    }

}