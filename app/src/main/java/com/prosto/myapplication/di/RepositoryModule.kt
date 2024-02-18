package com.prosto.myapplication.di

import android.content.Context
import com.prosto.myapplication.radioscreen.data.network.RetrofitClient
import com.prosto.myapplication.radioscreen.data.player.PlayerRepositoryImpl
import com.prosto.myapplication.radioscreen.data.radio.RadioRepositoryImpl
import com.prosto.myapplication.radioscreen.domain.player.api.PlayerRepository
import com.prosto.myapplication.radioscreen.domain.radio.api.RadioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRadioRepository(retrofitClient: RetrofitClient): RadioRepository =
        RadioRepositoryImpl(retrofitClient)

    @Provides
    @Singleton
    fun providePlayerRepository(@ApplicationContext context: Context): PlayerRepository =
        PlayerRepositoryImpl(context)
}