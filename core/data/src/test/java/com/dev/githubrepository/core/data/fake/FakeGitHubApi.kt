package com.dev.githubrepository.core.data.fake

import com.dev.githubrepository.core.data.api.GitHubApi
import com.dev.githubrepository.core.data.api.model.RepositoryResponse
import com.dev.githubrepository.core.data.api.model.SearchRepositoryResponse
import com.dev.githubrepository.core.data.api.model.UserResponse

internal class FakeGitHubApi(
    private val searchRepositoryResponsePages: List<SearchRepositoryResponse>,
    private val repositoryDetails: RepositoryResponse,
    private val userRepositories: List<RepositoryResponse>,
    private val userInfo: UserResponse
) : GitHubApi {
    override suspend fun searchRepositories(
        keyword: String,
        page: Int,
        perPage: Int
    ): SearchRepositoryResponse {
        return searchRepositoryResponsePages[page - 1]
    }

    override suspend fun getRepositoryDetails(owner: String, repo: String): RepositoryResponse {
        return repositoryDetails
    }

    override suspend fun getUserRepositories(username: String): List<RepositoryResponse> {
        return userRepositories
    }

    override suspend fun getUserInfo(username: String): UserResponse {
        return userInfo
    }
}