package com.dev.githubrepository.core.data.api

import com.dev.githubrepository.core.data.api.model.RepositoryResponse
import com.dev.githubrepository.core.data.api.model.SearchRepositoryResponse
import com.dev.githubrepository.core.data.api.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("/search/repositories?")
    suspend fun searchRepositories(
        @Query(value = "q", encoded = true) keyword: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): SearchRepositoryResponse

    @GET("/repos/{owner}/{repo}")
    suspend fun getRepositoryDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): RepositoryResponse

    @GET("/users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") username: String,
    ): List<RepositoryResponse>

    @GET("/users/{username}")
    suspend fun getUserInfo(
        @Path("username") username: String,
    ): UserResponse
}