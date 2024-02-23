package com.prosto.ruskoradio.di

import com.prosto.ruskoradio.radioscreen.domain.radio.api.RadioInteractor
import com.prosto.ruskoradio.radioscreen.domain.radio.api.RadioRepository
import com.prosto.ruskoradio.radioscreen.domain.radio.api.SongMapper
import com.prosto.ruskoradio.radioscreen.domain.radio.impl.RadioInteractorImpl
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
}