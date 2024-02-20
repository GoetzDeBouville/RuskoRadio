package com.prosto.myapplication.radioscreen.domain.prefs.impl

import com.prosto.myapplication.radioscreen.domain.prefs.api.PrefsInteractor
import com.prosto.myapplication.radioscreen.domain.prefs.api.PrefsRepository

class PrefsInteractorImpl(
    private val repository: PrefsRepository
) : PrefsInteractor {
    override fun saveValue(key: String, value: String) {
        repository.saveValue(key, value)
    }

    override fun getValue(key: String): String? {
        return repository.getValue(key)
    }

    override fun updateValue(key: String, newValue: String) {
        repository.updateValue(key, newValue)
    }
}