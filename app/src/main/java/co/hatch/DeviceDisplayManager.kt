package co.hatch

import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.hatch.deviceClientLib.connectivity.ConnectivityClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * This is the top-level manager for the device display
 *
 * @param parentView The parent View for the RecyclerView
 * @param connectivityClient The ConnectivityClient from which to retrieve data
 *
 */
class DeviceDisplayManager(
    private val parentView: View,
    private val connectivityClient: ConnectivityClient) {

    /**
     * Starts the device display.
     *  - Creates the RecyclerView to hold and manage the Views for the device entries
     *  - Periodically queries the connectivityClient for new data and updates the
     *    RecyclerView accordingly.
     */
    fun start() {
        val devicesView: RecyclerView = parentView.findViewById(R.id.device_recycler_view)
        devicesView.setLayoutManager(LinearLayoutManager(parentView.context))
        val deviceAdapter = DeviceAdapter()
        devicesView.adapter = deviceAdapter

        // The device discovery needs to happen on a worker thread
        Log.d(TAG, "Launching device discovery")
        CoroutineScope(Dispatchers.IO).launch {
            while(true) {
                val deviceUpdate = connectivityClient.discoverDevices().sortedWith(compareBy( { -it.rssi }, { it.name }))
                Log.d(TAG, "Devices discovered: ${deviceUpdate.size}")

                // The view update has to happen on the main thread
                CoroutineScope(Dispatchers.Main).launch {
                    deviceAdapter.updateDevices(deviceUpdate)
                }
                delay(TimeUnit.SECONDS.toMillis(UPDATE_FREQUENCY_SECONDS))
            }
        }
    }

    companion object {
        private const val UPDATE_FREQUENCY_SECONDS = 10L
        private val TAG = "HatchHomework.DeviceDisplayManager"
    }
}