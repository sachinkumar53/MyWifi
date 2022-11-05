package com.sachin.app.mywifi.data

import android.content.Context
import androidx.core.content.edit
import java.io.Serializable

class SettingsManager(context: Context) {
    private val preferences = context.getSharedPreferences("my_wifi", Context.MODE_PRIVATE)

    var credentials: Credentials? = null
        set(value) {
            field = value
            value?.let {
                preferences.edit {
                    putString(USERNAME, it.username)
                    putString(PASSWORD, it.password)
                }
            }
        }
        get() {
            val username = preferences.getString(USERNAME, null)
            val password = preferences.getString(PASSWORD, null)

            return if (username == null || password == null) {
                null
            } else {
                Credentials(username, password)
            }
        }

    companion object {
        private const val USERNAME = "username"
        private const val PASSWORD = "password"
    }
}


data class Credentials(
    val username: String,
    val password: String
):Serializable