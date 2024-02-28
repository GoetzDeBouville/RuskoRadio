package com.prosto.itunesservice.di

import com.prosto.itunesservice.domain.api.ItunesInteractor
import com.prosto.itunesservice.domain.api.ItunesRepository
import com.prosto.itunesservice.domain.impl.ItunesInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorItunesModule {
    @Provides
    @Singleton
    fun provideItunesInteractor(
        repository: ItunesRepository
    ): ItunesInteractor =
        ItunesInteractorImpl(repository)
}