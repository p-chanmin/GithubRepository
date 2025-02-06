package com.dev.githubrepository.core.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryResponse(
    @SerialName("id") val id: Int = 0,
    @SerialName("name") val name: String = "",
    @SerialName("language") val language: String? = null,
    @SerialName("stargazers_count") val stargazersCount: Int = 0,
    @SerialName("watchers_count") val watchersCount: Int = 0,
    @SerialName("forks_count") val forksCount: Int = 0,
    @SerialName("description") val description: String = "",
    @SerialName("topics") val topics: List<String> = emptyList(),
    @SerialName("owner") val owner: UserResponse = UserResponse(),
)