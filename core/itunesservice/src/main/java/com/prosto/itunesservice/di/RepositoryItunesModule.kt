package com.prosto.itunesservice.di

import com.prosto.itunesservice.data.ItunesRepositoryImpl
import com.prosto.itunesservice.data.network.ItunesRetrofitClient
import com.prosto.itunesservice.domain.api.ItunesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryItunesModule {
    @Provides
    @Singleton
    fun provideItunesRepository(
        client: ItunesRetrofitClient
    ): ItunesRepository = ItunesRepositoryImpl(client)
}