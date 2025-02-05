package com.dev.githubrepository.core.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("login") val login: String = "",
    @SerialName("avatar_url") val avatarUrl: String = "",
    @SerialName("followers") val followers: Int = 0,
    @SerialName("following") val following: Int = 0,
    @SerialName("public_repos") val publicRepos: Int = 0,
    @SerialName("bio") val bio: String = "",
)