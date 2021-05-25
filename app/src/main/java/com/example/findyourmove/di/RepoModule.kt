package com.example.findyourmove.di

import com.example.findyourmove.api.Constants
import com.example.findyourmove.api.TMDBService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun providesBaseUrl() = Constants.BASE_URL


    @Singleton
    @Provides
    fun providesTMDBServiceInstance(Base_Url:String) : TMDBService =

        Retrofit.Builder()
            .baseUrl(Base_Url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDBService::class.java)


}