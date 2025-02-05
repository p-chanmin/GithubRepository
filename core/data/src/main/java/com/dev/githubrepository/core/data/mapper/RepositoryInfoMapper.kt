package com.dev.githubrepository.core.data.mapper

import com.dev.githubrepository.core.data.api.model.RepositoryResponse
import com.dev.githubrepository.core.model.RepositoryInfo

internal fun RepositoryResponse.toData(): RepositoryInfo =
    RepositoryInfo(
        name = name,
        language = language,
        stargazersCount = stargazersCount,
        watchersCount = watchersCount,
        forksCount = forksCount,
        description = description,
        topics = topics,
        owner = owner.login,
        avatarUrl = owner.avatarUrl
    )