package com.iagoaf.appfinancas.services.database.keyValue

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveIsUserLoggedIn(loggedIn: Boolean) {
        prefs.edit().putBoolean("is_logged_in", loggedIn).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }

    fun saveToken(token: String) {
        prefs.edit().putString("auth_token", token).apply()
    }

    fun set(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun get(key: String): String? {
        return prefs.getString(key, null)
    }

    fun getToken(): String? {
        return prefs.getString("auth_token", null)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}
