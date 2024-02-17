package com.prosto.myapplication.di

import android.content.Context
import com.prosto.myapplication.core.utils.ConfigTool
import com.prosto.myapplication.radioscreen.data.converters.SongMapperImpl
import com.prosto.myapplication.radioscreen.data.network.ApiService
import com.prosto.myapplication.radioscreen.data.network.RetrofitClient
import com.prosto.myapplication.radioscreen.domain.player.api.SongMapper
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
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        ConfigTool.init(context)
        val baseUrl = ConfigTool.getAppConfig().songInfoUrl

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideSongMapper(): SongMapper = SongMapperImpl()

    @Provides
    @Singleton
    fun provideRetrofitClient(
        apiService: ApiService,
        @ApplicationContext context: Context
    ): RetrofitClient {
        return RetrofitClient(apiService, context)
    }
}