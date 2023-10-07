package com.dicoding.githubuserapp.data.retrofit

import com.dicoding.githubuserapp.BuildConfig
import com.dicoding.githubuserapp.data.response.DetailUserResponse
import com.dicoding.githubuserapp.data.response.GithubResponse
import com.dicoding.githubuserapp.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    fun getGithubUsers(
        @Query("q")
        query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    fun getDetailUser(
        @Path("username")
        username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    fun getFollowers(
        @Path("username")
        username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    fun getFollowing(
        @Path("username")
        username: String
    ): Call<List<ItemsItem>>
}