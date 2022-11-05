package com.sachin.app.mywifi

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.sachin.app.mywifi.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*binding.webView.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {

                    when (url) {
                        LOGIN -> {
                            invokeJs("document.getElementById('user').value = 'admin';")
                            invokeJs("document.getElementById('pwd').value = 'Sachin123@';") {
                                invokeJs("document.getElementById('login').click();")
                            }
                        }

                        INDEX -> {
                            binding.progressLayout.isVisible = false
                            isVisible = true

                            invokeJs("document.getElementById('qrcode').style.display = 'none';")
                            invokeJs("return document.getElementById('batt_percent').innerText;") {
                                Log.i(TAG, "onCreate: $it")
                            }
                        }
                    }

                }
            }

            loadUrl(LOGIN)
        }*/

    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.fragmentContainerView)
        if (
            navController.currentDestination?.id == R.id.loginFragment
            || navController.currentDestination?.id == R.id.dashboardFragment
        ) finish()
        else super.onBackPressed()
    }


    companion object {
        private const val TAG = "MainActivity"
        private const val LOGIN = "http://192.168.1.1/login.m.htm"
        private const val INDEX = "http://192.168.1.1/index.m.htm"
    }
}
