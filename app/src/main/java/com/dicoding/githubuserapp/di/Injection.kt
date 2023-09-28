package com.dicoding.githubuserapp.di

import android.content.Context
import com.dicoding.githubuserapp.data.repository.UserRepository
import com.dicoding.githubuserapp.data.retrofit.ApiConfig
import com.dicoding.githubuserapp.database.FavoriteRoomDatabase
import com.dicoding.githubuserapp.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteRoomDatabase.getFavDatabase(context)
        val dao = database.favDao()
        val appExecutors = AppExecutors()
        return UserRepository.getInstance(apiService, dao, appExecutors)
    }
}