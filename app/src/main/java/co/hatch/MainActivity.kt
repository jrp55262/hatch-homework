package co.hatch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import co.hatch.deviceClientLib.connectivity.ConnectivityClient


class MainActivity : AppCompatActivity() {

    private val connectivityClient = ConnectivityClient.Factory.create()

    /**
     * On activity creation, create the main layout and start the
     * DeviceDisplayManager.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        val contentView = LayoutInflater.from(this).inflate(R.layout.main_layout, null)
        setContentView(contentView)
        DeviceDisplayManager(contentView, connectivityClient).start()
    }

    companion object {
        private val TAG = "HatchHomework.MainActivity"
    }
}