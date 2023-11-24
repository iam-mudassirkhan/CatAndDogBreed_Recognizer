package com.mudassir.breedrecognizer

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "MyAppPreferences"
        private var instance: SharedPreferencesManager? = null

        fun getInstance(context: Context): SharedPreferencesManager {
            if (instance == null) {
                instance = SharedPreferencesManager(context)
            }
            return instance as SharedPreferencesManager
        }
    }

    // String
    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    // Boolean
    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    // Add more functions for other data types as needed
}

// Example Usage:

//// Save data
//val sharedPrefs = SharedPreferencesManager.getInstance(context)
//sharedPrefs.putString("username", "JohnDoe")
//sharedPrefs.putBoolean("isLoggedIn", true)
//
//// Retrieve data
//val username = sharedPrefs.getString("username")
//val isLoggedIn = sharedPrefs.getBoolean("isLoggedIn")
