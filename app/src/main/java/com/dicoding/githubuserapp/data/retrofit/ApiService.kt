package com.dicoding.githubuserapp.data.retrofit

import com.dicoding.githubuserapp.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getGithubUsers(
        @Query("q")
        query: String
    ): Call<GithubResponse>
}