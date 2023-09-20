package com.dicoding.githubuserapp.data.retrofit

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
    @Headers("Authorization: token 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")
    fun getGithubUsers(
        @Query("q")
        query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username")
        username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username")
        username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username")
        username: String
    ): Call<List<ItemsItem>>
}