package com.dicoding.githubuserapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.githubuserapp.data.retrofit.ApiService
import com.dicoding.githubuserapp.utils.AppExecutors
import com.dicoding.githubuserapp.data.Result
import com.dicoding.githubuserapp.data.response.DetailUserResponse
import com.dicoding.githubuserapp.database.Favorite
import com.dicoding.githubuserapp.database.FavoriteDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(
    private val apiService: ApiService,
    private val favDao: FavoriteDao,
    private val appExecutors: AppExecutors
){
    private val result = MediatorLiveData<Result<Favorite>>()

    fun insertUser(username: String) {
        val client = apiService.getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    val detailUser = response.body()
                    appExecutors.diskIO.execute {
                        val user = Favorite(
                            detailUser?.login.toString(),
                            detailUser?.avatarUrl,
                            detailUser?.url
                        )
                        favDao.insert(user)
                    }
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
    }

    fun deleteUser(username: String) {
        val client = apiService.getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    val detailUser = response.body()
                    appExecutors.diskIO.execute {
                        val user = Favorite(
                            detailUser?.login.toString(),
                            detailUser?.avatarUrl,
                            detailUser?.url
                        )
                        favDao.delete(user)
                    }
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
    }

    fun getFavoriteUserByUsername(username: String): LiveData<Favorite> {
        return favDao.getFavoriteUserByUsername(username)
    }
    fun getFavoriteUser(): LiveData<List<Favorite>> {
        return favDao.getFavoriteUser()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favDao: FavoriteDao,
            appExecutors: AppExecutors
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, favDao, appExecutors)
            }.also { instance = it }
    }
}