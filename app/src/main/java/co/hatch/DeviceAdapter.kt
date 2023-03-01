package co.hatch

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.hatch.deviceClientLib.model.Device

class DeviceAdapter(var devices: List<Device>):
    RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    constructor(): this(listOf<Device>())

    fun updateDevices(deviceUpdate: List<Device>) {
        devices = deviceUpdate
        notifyDataSetChanged()
    }

    class DeviceViewHolder(private val deviceView: View): RecyclerView.ViewHolder(deviceView) {
        fun bind(device: Device) {
            Log.d(TAG, "Binding device ${device.name}")
            val nameTextView: TextView = deviceView.findViewById(R.id.device_name)
            val connectedTextView: TextView = deviceView.findViewById(R.id.device_connected)
            val rssiTextView: TextView = deviceView.findViewById(R.id.device_rssi)
            nameTextView.text = "Device: ${device.name}"
            connectedTextView.text = if (device.connected) "Connected" else "Disconnected"
            rssiTextView.text = "rssi: ${device.rssi.toString()}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.device_entry, parent, false)
        return DeviceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(devices[position])
    }

    companion object {
        private val TAG = "DeviceAdapter"
    }
}