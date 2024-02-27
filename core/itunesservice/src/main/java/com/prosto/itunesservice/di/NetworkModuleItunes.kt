package com.prosto.itunesservice.di

import android.content.Context
import com.hellcorp.di_qualifiers.ItunesRetrofit
import com.prosto.itunesservice.data.network.ItunesApiService
import com.prosto.itunesservice.data.network.ItunesRetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModuleItunes {
    @Provides
    @Singleton
    @ItunesRetrofit
    fun provideRetrofitItunes(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ItunesRetrofitClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideItunesApiService(@ItunesRetrofit retrofit: Retrofit): ItunesApiService =
        retrofit.create(ItunesApiService::class.java)

    @Provides
    @Singleton
    fun provideItunesRetrofitClient(
        service: ItunesApiService,
        @ApplicationContext context: Context
    ): ItunesRetrofitClient {
        return ItunesRetrofitClient(service, context)
    }
}