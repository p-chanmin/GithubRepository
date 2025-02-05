package com.dev.githubrepository.core.data.mapper

import com.dev.githubrepository.core.data.api.model.UserResponse
import com.dev.githubrepository.core.model.UserInfo

internal fun UserResponse.toData(): UserInfo =
    UserInfo(
        username = login,
        avatarUrl = avatarUrl,
        followers = followers,
        following = following,
        publicRepos = publicRepos,
        bio = bio
    )