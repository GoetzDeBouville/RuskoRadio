package com.prosto.myapplication.radioscreen.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hellcorp.restquest.domain.network.models.LoadingStatus
import com.prosto.myapplication.core.ui.BaseViewModel
import com.prosto.myapplication.radioscreen.domain.radio.api.RadioInteractor
import com.prosto.myapplication.radioscreen.domain.radio.models.RadioState
import com.prosto.myapplication.radioscreen.dto.SongEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RadioViewModel @Inject constructor(
    private val radioInteractor: RadioInteractor
) : BaseViewModel() {
    private val _radioState = MutableStateFlow<RadioState>(RadioState.Loading)
    val radioState: StateFlow<RadioState>
        get() = _radioState

    init {
        getSongTitle()
    }

    private fun getSongTitle() {
        viewModelScope.launch {
            val tickerFlow = flow {
                while (true) {
                    emit(Unit)
                    delay(3000)
                }
            }

            tickerFlow.flatMapLatest {
                radioInteractor.updateTitle()
            }.collect { result ->
                processResult(result)
            }
        }
    }


    private suspend fun processResult(result: Pair<SongEntity?, LoadingStatus?>) {
        Log.i("MyLog", "result = $result")
        if (result.second == null) {
            _radioState.value = RadioState.Content(result.first!!)
        } else {
            _radioState.value = RadioState.ConnectionError
        }
    }
}