package com.dev.githubrepository.core.model

data class UserInfo(
    val username: String = "",
    val avatarUrl: String = "",
    val followers: Int = 0,
    val following: Int = 0,
    val publicRepos: Int = 0,
    val bio: String = "",
)