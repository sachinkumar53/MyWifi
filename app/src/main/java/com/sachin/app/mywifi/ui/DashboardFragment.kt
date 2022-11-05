package com.sachin.app.mywifi.ui

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import com.sachin.app.mywifi.Constants
import com.sachin.app.mywifi.R
import com.sachin.app.mywifi.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private val binding: FragmentDashboardBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.apply {
            loadUrl(Constants.DASHBOARD_URL)
        }
    }
}