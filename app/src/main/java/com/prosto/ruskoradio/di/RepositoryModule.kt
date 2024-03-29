package com.prosto.ruskoradio.di

import com.prosto.ruskoradio.radioscreen.data.network.RetrofitClient
import com.prosto.ruskoradio.radioscreen.data.radio.RadioRepositoryImpl
import com.prosto.ruskoradio.radioscreen.domain.radio.api.RadioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRadioRepository(retrofitClient: RetrofitClient): RadioRepository =
        RadioRepositoryImpl(retrofitClient)
}