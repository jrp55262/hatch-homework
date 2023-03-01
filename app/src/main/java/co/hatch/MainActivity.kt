package co.hatch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.hatch.deviceClientLib.connectivity.ConnectivityClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val connectivityClient = ConnectivityClient.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        val devicesView: RecyclerView = findViewById(R.id.device_recycler_view)
        devicesView.setLayoutManager(LinearLayoutManager(this))

        // The device discovery needs to happen on a worker thread
        Log.d(TAG, "Launching device discovery")
        CoroutineScope(Dispatchers.IO).launch {
            val deviceList = connectivityClient.discoverDevices().sortedBy { it.rssi }
            val deviceAdapter = DeviceAdapter(deviceList)
            CoroutineScope(Dispatchers.Main).launch {
                devicesView.adapter = deviceAdapter
            }

            while(true) {
                delay(TimeUnit.SECONDS.toMillis(UPDATE_FREQUENCY_SECONDS))
                val deviceUpdate = connectivityClient.discoverDevices().sortedBy { it.rssi }
                Log.d(TAG, "Devices discovered: ${deviceUpdate.size}")

                // The view update has to happen on the main thread
                CoroutineScope(Dispatchers.Main).launch {
                    deviceAdapter.updateDevices(deviceUpdate)
                }
            }
        }
    }

    companion object {
        private const val UPDATE_FREQUENCY_SECONDS = 10L
        private val TAG = "HatchHomework"
    }
}