package co.hatch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import co.hatch.deviceClientLib.model.Device

class DeviceEntryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0): LinearLayout(context, attrs, defStyle, defStyleRes), GestureDetector.OnGestureListener {
    var device: Device? = null
    private val detector = GestureDetectorCompat(this.context, this)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (detector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onDown(event: MotionEvent): Boolean {
        Log.d(TAG, "onDown ${event.action}")
        return false

    }

    override fun onShowPress(event: MotionEvent) {
        Log.d(TAG, "onShowPress ${event.action}")
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        Log.d(TAG, "onSingleTapUp ${event.action}")
        return false
    }

    override fun onScroll(event0: MotionEvent, event1: MotionEvent, p2: Float, p3: Float): Boolean {
        Log.d(TAG, "onScroll ${event0.action} -> ${event1.action}")
        return false
    }

    override fun onLongPress(event: MotionEvent) {
        Log.d(TAG, "onLongPress ${event.action}")
        device?.also { device ->
            val toast = Toast.makeText(
                this.context,
                "Device ${device.name} connected: ${device.connected}",
                Toast.LENGTH_LONG
            )
            toast.show()
        }
    }

    override fun onFling(event0: MotionEvent, event1: MotionEvent, p2: Float, p3: Float): Boolean {
        Log.d(TAG, "onFling ${event0.action} -> ${event1.action}")
        return false
    }

    companion object {
        private val TAG = "HatchHomework.DeviceEntryView"
    }
}