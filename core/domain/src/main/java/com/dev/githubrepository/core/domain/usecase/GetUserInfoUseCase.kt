package com.dev.githubrepository.core.domain.usecase

import com.dev.githubrepository.core.data_api.GitHubRepository
import com.dev.githubrepository.core.model.UserInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val gitHubRepository: GitHubRepository
) {
    operator fun invoke(username: String): Flow<UserInfo> =
        gitHubRepository.getUserInfo(username)
}