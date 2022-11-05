package com.sachin.app.mywifi.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sachin.app.mywifi.Constants
import com.sachin.app.mywifi.R
import com.sachin.app.mywifi.data.Credentials
import com.sachin.app.mywifi.data.SettingsManager
import com.sachin.app.mywifi.databinding.FragmentLoginBinding
import com.sachin.app.mywifi.invokeJs

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding: FragmentLoginBinding by viewBinding()
    private lateinit var settingsManager: SettingsManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        settingsManager = SettingsManager(context.applicationContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val credentials = settingsManager.credentials

        binding.webView.apply {
            webChromeClient = object : WebChromeClient() {
                override fun onJsAlert(
                    view: WebView?,
                    url: String?,
                    message: String?,
                    result: JsResult?
                ): Boolean {
                    Log.i("Sachin", "onJsAlert: url= $url\nmsg= $message")
                    if (message == "error") {
                        showProgressbar(false)
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        return true
                    }
                    return super.onJsAlert(view, url, message, result)
                }
            }

            webViewClient = object : WebViewClient() {

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    Log.i("Sachin", "onPageFinished: $url")
                    when (url) {
                        Constants.DASHBOARD_URL -> findNavController().navigate(
                            R.id.action_loginFragment_to_dashboardFragment
                        )

                        Constants.LOGIN_URL -> {
                            binding.webView.invokeJs(
                                "document.getElementById('error_info').addEventListener('DOMSubtreeModified',(e)=>{" +
                                        "alert('error');" +
                                        "});"
                            )

                            if (credentials == null) {
                                binding.run {
                                    usernameInput.isVisible = true
                                    passwordInput.isVisible = true
                                    login.isVisible = true
                                }
                            } else logIn(credentials)
                        }
                    }
                }
            }
            loadUrl(Constants.LOGIN_URL)
        }

        binding.run {
            login.setOnClickListener {
                val username = username.text
                val password = password.text
                if (username.isNullOrBlank() or password.isNullOrBlank()) {
                    Toast.makeText(
                        requireContext(),
                        "Enter username and password to login.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else logIn(
                    Credentials(
                        username.toString(),
                        password.toString()
                    )
                )
            }
        }
    }

    private fun logIn(credentials: Credentials) = binding.webView.run {
        Log.i("Sachin", "logIn with credentials: $credentials ")
        showProgressbar(true)
        invokeJs("document.getElementById('user').value = '${credentials.username}';")
        invokeJs("document.getElementById('pwd').value = '${credentials.password}';") {
            invokeJs("document.getElementById('login').click();")
        }
    }

    private fun showProgressbar(show: Boolean) = binding.run {
        usernameInput.isClickable = !show
        passwordInput.isClickable = !show
        login.isEnabled = !show
        loading.isVisible = show
    }
}