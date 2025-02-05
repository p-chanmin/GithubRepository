package com.dev.githubrepository.core.domain.usecase

import com.dev.githubrepository.core.data_api.GitHubRepository
import com.dev.githubrepository.core.model.RepositoryInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoriesUseCase @Inject constructor(
    private val gitHubRepository: GitHubRepository
) {
    operator fun invoke(keyword: String, perPage: Int): Flow<List<RepositoryInfo>> =
        gitHubRepository.searchRepositories(keyword, perPage)
}