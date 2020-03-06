/*
* Preston Bennett
* App 5: Target Game App
* Dr. Mark Smith | CSCI 4357 Programming Mobile Devices
*
* This app uses Android View, Timers, and other widgets to create shapes and animations for
* a custom rendered game.
*
* Student's Note: Unfortunately, one of the widgets on this app does not scale properly. I
* couldn't figure out how to scale the cannon rectangle. Besides this, everything else
* should be functional. Also, a different scaling method was use for the physics. The given rules did not function well.
* Recommended testing should be done with the a Galaxy Nexus Emulator for the best experience.
*
* Please forward questions and concerns to pbennett2@cub.uca.edu
* */

package psb.csci4357.app5

import android.content.DialogInterface
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var timer = Timer()

    companion object
    {
        private var instance : MainActivity? = null

        public fun getInstance() : MainActivity
        {
            return instance!!
        }
        public var run : Boolean = false
        public var speed = 0
        public var score = 0
        public var shot = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (supportActionBar != null)
            supportActionBar?.hide() //Hide Title Bar
        instance = this
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setting handler
        var handler = Handler()
        seekBar.setOnSeekBarChangeListener(handler)
        seekBar2.setOnSeekBarChangeListener(handler)
        button.setOnClickListener(handler)

        var timerTask = TimerObject()
        timer.schedule(timerTask,0,25)
    }

    inner class Handler : View.OnClickListener, SeekBar.OnSeekBarChangeListener,
        DialogInterface.OnClickListener {
        override fun onClick(p0: View?) {
            shot++
            shots.setText(shot.toString())
            run = true
        }

        override fun onClick(p0: DialogInterface?, p1: Int) {
            if (p1 == DialogInterface.BUTTON_POSITIVE) {
                MyView.reset()
                shot = 0
                score = 0
                shots.setText(shot.toString())
                scoreCount.setText(score.toString())
                p0?.dismiss()
            }
        }

        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            if (p0 == seekBar) {
                MyView.setCAngle(p1.toFloat())
                angle.setText(p1.toString())
                MyView.getInstance().invalidate()
            }
            else if (p0 == seekBar2) {
                speed = p1
                velocity.setText(p1.toString())
            }
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {

        }

        override fun onStopTrackingTouch(p0: SeekBar?) {

        }
    }

    class TimerObject : TimerTask()
    {
        override fun run()
        {
            var helper = HelperThread()

            MainActivity.getInstance().runOnUiThread(helper)
        }
    }

    class HelperThread : Runnable
    {
        override fun run()
        {
            MainActivity.getInstance().update()
        }
    }

    public fun update() {
        scoreCount.setText(score.toString())
        if (run) {
            if (MyView.getStart() == true) {
                val ballCoords = MyView.getBallCoords()
                val angleRad = (MyView.getCAngle() * (Math.PI / 180))
                val dx = Math.sin(angleRad)
                val dy = Math.cos(angleRad)
                val x1 = (ballCoords.left + dx * speed).toInt()//(ballCoords.left + ((speed) * 1 * Math.cos(angleRad))).toInt()
                val y1 = (ballCoords.top - dy * speed).toInt()//(ballCoords.top + ((speed) * 1 * Math.sin(angleRad))).toInt()
                val x2 = (ballCoords.right + dx * speed).toInt()//(ballCoords.right + ((speed) * 1 * Math.cos(angleRad))).toInt()
                val y2 = (ballCoords.bottom - dy * speed).toInt()//(ballCoords.bottom + ((speed) * 1 * Math.sin(angleRad))).toInt()
                MyView.setBallCoords(x1, y1, x2, y2)
                println("($x1,$y1,$x2,$y2)")

                if ((y1 <= 0 || y1 > MyView.height1) || (x1 > MyView.width1)) {
                    MyView.setStart(false)
                    val cannon = MyView.getCannon()
                    MyView.setBallCoords(cannon.left.toInt(),cannon.top.toInt()-40,cannon.right.toInt()+10,cannon.top.toInt())
                    run = false
                    println("boundary hit")
                }
                if (MyView.isOver()) {
                    //Display alert box
                    var handler = Handler()
                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setMessage("Game Over!")
                    dialogBuilder.setPositiveButton("OK", handler)
                    val alert1 = dialogBuilder.create()
                    alert1.setTitle("Game Over!")
                    alert1.show()
                }

                MyView.getInstance().invalidate()
                //Old Scale Factor
                /**
                 * val ballCoords = MyView.getBallCoords()
                val angleRad = MyView.getCAngle() * (Math.PI / 180)
                val x1 = ((speed * (Math.cos(angleRad) * t)) * ballCoords.left).toInt() //left
                val y1 = (((-speed * (Math.sin(angleRad) * t)) + 0.5 * GRAVITY * t * t) + ballCoords.top).toInt() //top
                val x2 = ((speed * (Math.cos(angleRad) * t)) * ballCoords.right).toInt() //right
                val y2 = (((-speed * (Math.sin(angleRad) * t)) + 0.5 * GRAVITY * t * t) + ballCoords.bottom).toInt() //bottom
                MyView.setBallCoords(x1, y1, x2, y2)
                t++
                println(t.toString() + " in use\n")
                 *
                 */

            } else {
                var ballCoords = MyView.getBallCoords()
                val centerx1 = ballCoords.left
                val centery1 = ballCoords.top + 100
                val centerx2 = ballCoords.right
                val centery2 = ballCoords.bottom + 100
                val angleRad = MyView.getCAngle() * (Math.PI / 180)
                val x1 = (Math.cos(angleRad) * (ballCoords.left - centerx1) - Math.sin(angleRad) * (ballCoords.top - centery1) + centerx1).toInt()
                val y1 = (Math.sin(angleRad) * (ballCoords.left - centerx1) + Math.cos(angleRad) * (ballCoords.top - centery1) + centery1).toInt()
                val x2 = (Math.cos(angleRad) * (ballCoords.right - centerx2) - Math.sin(angleRad) * (ballCoords.bottom - centery2) + centerx2).toInt()//ballCoords.right + ratio
                val y2 = (Math.sin(angleRad) * (ballCoords.right - centerx2) + Math.cos(angleRad) * (ballCoords.bottom - centery2) + centery2).toInt()//ballCoords.bottom + ratio
                MyView.setBallCoords(x1, y1, x2, y2)
                MyView.setStart(true)
                MyView.getInstance().invalidate()
            }
        }
    }
}


