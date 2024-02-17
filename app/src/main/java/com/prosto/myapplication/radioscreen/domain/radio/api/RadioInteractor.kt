package com.prosto.myapplication.radioscreen.domain.radio.api

import com.hellcorp.restquest.domain.network.models.LoadingStatus
import com.hellcorp.restquest.domain.network.models.Resource
import com.prosto.myapplication.radioscreen.dto.SongEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

interface RadioInteractor {

    suspend fun updateTitle(): Flow<Pair<SongEntity?, LoadingStatus?>>
}