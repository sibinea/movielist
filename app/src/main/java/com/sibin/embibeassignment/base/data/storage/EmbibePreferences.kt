package com.sibin.embibeassignment.base.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.sibin.embibeassignment.base.extension.empty
import javax.inject.Inject


interface EmbibePreferences {

    companion object {
        const val PREFERENCE_NAME = "embibe_shared_preferences"
    }

    fun putData(key: String, data: String)

    fun putData(key: String, data: Int)

    fun putData(key: String, data: Boolean)

    fun getData(key: String): String?

    fun getIntData(key: String): Int

    fun getBooleanData(key: String): Boolean

    class Preferences
    @Inject constructor(context: Context) :
        EmbibePreferences {

        private var preferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

        override fun putData(key: String, data: String) {
            preferences.edit().putString(key, data).apply()
        }

        override fun putData(key: String, data: Int) {
            preferences.edit().putInt(key, data).apply()
        }

        override fun putData(key: String, data: Boolean) {
            preferences.edit().putBoolean(key, data).apply()
        }

        override fun getData(key: String): String? {
            return preferences.getString(key, String.empty())
        }

        override fun getIntData(key: String): Int {
            return preferences.getInt(key, 1)
        }

        override fun getBooleanData(key: String): Boolean {
            return preferences.getBoolean(key, false)
        }
    }
}