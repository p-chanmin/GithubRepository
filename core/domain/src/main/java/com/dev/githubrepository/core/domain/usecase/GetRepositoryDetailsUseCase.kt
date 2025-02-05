package com.dev.githubrepository.core.domain.usecase

import com.dev.githubrepository.core.data_api.GitHubRepository
import com.dev.githubrepository.core.model.RepositoryInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRepositoryDetailsUseCase @Inject constructor(
    private val gitHubRepository: GitHubRepository
) {
    operator fun invoke(owner: String, repo: String): Flow<RepositoryInfo> =
        gitHubRepository.getRepositoryDetails(owner, repo)
}