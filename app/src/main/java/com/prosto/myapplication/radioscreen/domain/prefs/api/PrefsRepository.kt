package com.prosto.myapplication.radioscreen.domain.prefs.api

interface PrefsRepository {
    fun saveValue(key: String, value: String)
    fun getValue(key: String): String?
    fun updateValue(key: String, newValue: String)
}
