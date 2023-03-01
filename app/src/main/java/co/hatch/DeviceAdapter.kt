package co.hatch

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.hatch.deviceClientLib.model.Device
import java.text.DateFormat

/**
 * RecyclerViewAdaper for managing Device views
 */
class DeviceAdapter(var devices: List<Device>):
    RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    constructor(): this(listOf<Device>())

    /**
     * Update the list of devices that this adapter represents.
     *
     * TODO - notifyDataSetChanged() is an expedient big hammer,
     * but it's expensive.  Update this to use DiffUtils
     */
    fun updateDevices(deviceUpdate: List<Device>) {
        devices = deviceUpdate
        notifyDataSetChanged()
    }

    class DeviceViewHolder(val deviceView: DeviceEntryView): RecyclerView.ViewHolder(deviceView) {

        /**
         * Update the text fields in the device_entry view with the
         * values in this Device entry.
         *
         * @param device The Device entry to bind to this view
         *
         * TODO - Use Android resources rather than hardcoded strings
         */
        fun bind(device: Device) {
            Log.d(TAG, "Binding device ${device.name}")
            val nameTextView: TextView = deviceView.findViewById(R.id.device_name)
            val connectedTextView: TextView = deviceView.findViewById(R.id.device_connected)
            val rssiTextView: TextView = deviceView.findViewById(R.id.device_rssi)
            nameTextView.text = "Device: ${device.name}"
            val lastConnected = device.latestConnectedTime?.let {
                dateFormat.format(it)
            } ?: "Never"
            connectedTextView.text = "Last Connected: $lastConnected"
            rssiTextView.text = "rssi: ${device.rssi.toString()}"
        }
    }

    /**
     * Create a DeviceViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.device_entry, parent, false) as DeviceEntryView
        return DeviceViewHolder(view)
    }

    /**
     * Get the count of devices being managed
     */
    override fun getItemCount(): Int {
        return devices.size
    }

    /**
     * Bind a DeviceViewHolder to a specific Device entry
     *
     * @param holder the DeviceViewHolder to bind
     * @param position The position in the devices list representing the item to be bound
     */
    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = devices[position]
        holder.bind(device)
        holder.deviceView.device = device
        val view = holder.deviceView
    }

    companion object {
        private val TAG = "DeviceAdapter"
        private val dateFormat = DateFormat.getDateInstance()
    }
}