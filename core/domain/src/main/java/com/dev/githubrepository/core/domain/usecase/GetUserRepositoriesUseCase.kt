package com.dev.githubrepository.core.domain.usecase

import com.dev.githubrepository.core.data_api.GitHubRepository
import com.dev.githubrepository.core.model.RepositoryInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserRepositoriesUseCase @Inject constructor(
    private val gitHubRepository: GitHubRepository
) {
    operator fun invoke(username: String): Flow<List<RepositoryInfo>> =
        gitHubRepository.getUserRepositories(username)
}