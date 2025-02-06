package com.dev.githubrepository.core.model

data class RepositoryInfo(
    val id: Int = 0,
    val name: String = "",
    val language: String? = null,
    val stargazersCount: Int = 0,
    val watchersCount: Int = 0,
    val forksCount: Int = 0,
    val description: String = "",
    val topics: List<String> = emptyList(),
    val owner: String = "",
    val avatarUrl: String = "",
)