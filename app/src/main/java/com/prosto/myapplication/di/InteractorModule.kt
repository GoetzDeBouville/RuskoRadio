package com.prosto.myapplication.di

import com.prosto.myapplication.radioscreen.domain.player.api.PlayerInteractor
import com.prosto.myapplication.radioscreen.domain.player.api.PlayerRepository
import com.prosto.myapplication.radioscreen.domain.player.api.SongMapper
import com.prosto.myapplication.radioscreen.domain.player.impl.PlayerInteractorImpl
import com.prosto.myapplication.radioscreen.domain.radio.api.RadioInteractor
import com.prosto.myapplication.radioscreen.domain.radio.api.RadioRepository
import com.prosto.myapplication.radioscreen.domain.radio.impl.RadioInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {
    @Provides
    @Singleton
    fun provideRadioInteractor(
        radioRepository: RadioRepository,
        converter: SongMapper
    ): RadioInteractor =
        RadioInteractorImpl(
            radioRepository,
            converter
        )

    @Provides
    @Singleton
    fun providePlayerInteractor(playerRepository: PlayerRepository): PlayerInteractor =
        PlayerInteractorImpl(playerRepository)
}