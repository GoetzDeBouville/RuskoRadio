package com.prosto.myapplication.radioscreen.domain.prefs.api

interface PrefsInteractor {
    fun saveValue(key: String, value: String)
    fun getValue(key: String): String?
    fun updateValue(key: String, newValue: String)
}