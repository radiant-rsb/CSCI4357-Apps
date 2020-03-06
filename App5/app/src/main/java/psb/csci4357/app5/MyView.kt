package psb.csci4357.app5

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.graphics.*
import android.graphics.drawable.Drawable
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.view.*


class MyView : View {
    //private var GRAVITY = 9.8f
    //private var Cangle : Float = 0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var path : Path = Path()
    //private var ballCoords : Rect = Rect(0,0,0,0)
    //private var cannon : RectF = RectF(0f, 0f,0f, 0f)
    //private var start : Boolean = false
    var ballImage = ImageView(MainActivity.getInstance())

    companion object
    {
        private var instance : MyView?  = null
        public fun getInstance() : MyView
        {
            return instance!!
        }
        private var Cangle : Float = 0f
        public fun setCAngle(x : Float) {
            this.Cangle = x
        }

        public fun getCAngle() : Float {
            return Cangle
        }

        private var cannon : RectF = RectF(0f, 0f,0f, 0f)
        public fun setCannon(ux : Float, uy : Float, lx : Float, ly : Float) {
            this.cannon.set(ux,uy,lx,ly)
        }
        public fun getCannon() : RectF
        {
            return cannon
        }

        private var ballCoords : Rect = Rect(0,0,0,0)
        public fun setBallCoords(ux : Int, uy : Int, lx : Int, ly : Int)
        {
            this.ballCoords.set(ux,uy,lx,ly)
        }

        public fun getBallCoords() : Rect
        {
            return ballCoords
        }

        private var start : Boolean = false
        public fun setStart(x : Boolean) {
            this.start = x
        }
        public fun getStart() : Boolean {
            return start
        }

        public fun reset() {
            var start_x = (0.5 * width1).toInt()
            var start_y = (0.2 * height1).toInt()
            var offset = (0.1 * height1).toInt()
            var x : Int
            var y = start_y
            for (i in 0..6) {
                x = start_x
                for (j in 0..6) {
                    var imageView = ImageView(MainActivity.getInstance())
                    imageView.setImageResource(R.drawable.untouched)
                    var drawable = imageView.getDrawable()
                    drawable.setBounds(x,y,x+offset,y+offset) //Sets the dimensions
                    targets.add(drawable)  //stores away the image
                    x += offset
                }
                y += offset
            }
        }
        public var targets = ArrayList<Drawable>()
        public fun isOver() : Boolean {
            return targets.isEmpty()
        }
        public var width1 : Int = 0
        public var height1 : Int = 0
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        instance = this
        ballImage.setImageResource(R.drawable.cannon_ball)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        var width = this.getWidth()
        var height = this.getHeight()
        width1 = width
        height1 = height

        var start_x = (0.5 * width).toInt()
        var start_y = (0.2 * height).toInt()
        var offset = (0.1 * height).toInt()
        Cangle = 0f

        cannon = RectF(45f,490f,65f,570f)

        ballCoords = Rect(cannon.left.toInt(),cannon.top.toInt()-40,cannon.right.toInt()+10,cannon.top.toInt())


        var x : Int
        var y = start_y
        for (i in 0..6) {
            x = start_x
            for (j in 0..6) {
                var imageView = ImageView(MainActivity.getInstance())
                imageView.setImageResource(R.drawable.untouched)
                var drawable = imageView.getDrawable()
                drawable.setBounds(x,y,x+offset,y+offset) //Sets the dimensions
                targets.add(drawable)  //stores away the image
                x += offset
            }
            y += offset
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var drawable : Drawable
        paint.setColor(Color.BLACK)
        path.addRect(cannon,Path.Direction.CW)
        canvas.save()
        canvas.rotate(Cangle,cannon.right, cannon.bottom)
        canvas.drawPath(path,paint)
        canvas.restore()
        if (start) {
            drawable = ballImage.getDrawable()
            drawable.setBounds(ballCoords)
            drawable.draw(canvas)

            for (i in 0..targets.size-1) {
                if (i >= targets.size)
                    break
                var target = targets[i].getBounds()
                if ((ballCoords.left > target.left) && (ballCoords.top > target.top) &&
                    (ballCoords.left < target.right) && (ballCoords.top < target.bottom)) {
                    targets.removeAt(i)
                    MainActivity.score++
                }
            }
        }

        for (i in 0..targets.size-1)
        {
            drawable = targets.get(i)
            drawable.draw(canvas)
        }

    }
}