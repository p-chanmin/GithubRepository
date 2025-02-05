package com.dev.githubrepository.core.data_api

import com.dev.githubrepository.core.model.RepositoryInfo
import com.dev.githubrepository.core.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface GitHubRepository {
    fun searchRepositories(keyword: String, perPage: Int): Flow<List<RepositoryInfo>>

    fun getRepositoryDetails(owner: String, repo: String): Flow<RepositoryInfo>

    fun getUserRepositories(username: String): Flow<List<RepositoryInfo>>

    fun getUserInfo(username: String): Flow<UserInfo>

    fun cacheClear()
}