package com.sachin.app.mywifi.ui

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sachin.app.mywifi.Constants
import com.sachin.app.mywifi.R
import com.sachin.app.mywifi.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding: FragmentMainBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wifiManager =
            requireContext().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (!wifiManager.isWifiEnabled || wifiManager.connectionInfo == null) {
            binding.run {
                noWifi.isVisible = true
                errorMessage.isVisible = true
                errorMessage.setText(R.string.wifi_is_not_connected)
            }
        } else {
            val ip = wifiManager.connectionInfo.ipAddress
            val ipString = String.format(
                "%d.%d.%d.%d",
                ip and 0xff,
                ip shr 8 and 0xff,
                ip shr 16 and 0xff,
                ip shr 24 and 0xff
            )
            Log.i(TAG, "onViewCreated: IP =  $ipString")

            if (!ipString.startsWith(Constants.IP_ADDRESS)) {
                binding.run {
                    unknownWifi.isVisible = true
                    errorMessage.isVisible = true
                    errorMessage.setText(R.string.unknown_wifi_detected)
                }
            } else {
                findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
            }
        }

    }

    companion object {
        private const val TAG = "MainFragment"
    }

}