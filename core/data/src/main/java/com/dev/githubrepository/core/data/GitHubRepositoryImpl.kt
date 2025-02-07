package com.dev.githubrepository.core.data

import androidx.annotation.VisibleForTesting
import com.dev.githubrepository.core.data.api.GitHubApi
import com.dev.githubrepository.core.data.api.model.RepositoryResponse
import com.dev.githubrepository.core.data.mapper.toData
import com.dev.githubrepository.core.data_api.GitHubRepository
import com.dev.githubrepository.core.model.RepositoryInfo
import com.dev.githubrepository.core.model.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val gitHubApi: GitHubApi,
) : GitHubRepository {

    @VisibleForTesting
    var page = 1
    @VisibleForTesting
    var endPage = false

    @VisibleForTesting
    var cacheKeyword = ""
    @VisibleForTesting
    val cacheList = mutableListOf<RepositoryResponse>()

    override fun searchRepositories(
        keyword: String,
        perPage: Int
    ): Flow<List<RepositoryInfo>> = flow {
        if (cacheKeyword == keyword) {
            page++
        } else {
            cacheClear()
        }
        val response = gitHubApi.searchRepositories(
            keyword = keyword,
            page = page,
            perPage = perPage,
        )
        emit(response)
    }.onEach { response ->
        cacheKeyword = keyword
        endPage = response.incompleteResults
        cacheList.addAll(response.items)
    }.map {
        cacheList.map {
            it.toData()
        }
    }

    override fun getRepositoryDetails(
        owner: String,
        repo: String
    ): Flow<RepositoryInfo> = flow {
        val response = gitHubApi.getRepositoryDetails(owner = owner, repo = repo)
        emit(response)
    }.map {
        it.toData()
    }

    override fun getUserRepositories(username: String): Flow<List<RepositoryInfo>> = flow {
        val response = gitHubApi.getUserRepositories(username)
        emit(response)
    }.map { response ->
        response.map {
            it.toData()
        }
    }

    override fun getUserInfo(username: String): Flow<UserInfo> = flow {
        val response = gitHubApi.getUserInfo(username)
        emit(response)
    }.map {
        it.toData()
    }

    override fun cacheClear() {
        page = 1
        endPage = false
        cacheKeyword = ""
        cacheList.clear()
    }
}