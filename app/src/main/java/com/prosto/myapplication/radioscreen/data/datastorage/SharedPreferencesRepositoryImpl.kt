package com.prosto.myapplication.radioscreen.data.datastorage

import android.content.Context
import android.content.SharedPreferences
import com.prosto.myapplication.radioscreen.domain.prefs.api.PrefsRepository

class SharedPreferencesRepositoryImpl(context: Context) : PrefsRepository {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    override fun saveValue(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun getValue(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun updateValue(key: String, newValue: String) {
        saveValue(key, newValue) // Так как логика та же, что и для сохранения нового значения
    }
}