/*
* Preston Bennett
* App 1: Flashcard Simulator / Flag Quiz
* Dr. Mark Smith | CSCI 4357 Programming Mobile Devices
*
* This app uses threading to update two non-interactive XML elements
* (imageView, textView) to simulate a timed flashcard quiz with international
* flags and nation names.
*
* Please forward questions and concerns to pbennett2@cub.uca.edu
* */


package com.example.flagquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        var quiz = Quiz(10, 10) //Creates quiz object which controls UI elements
        quiz.start()
    }
}
