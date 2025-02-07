package com.dev.githubrepository.feature.repository.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class RepositoryUiState(
    val showProgress: Boolean = false,
    val showBottomSheet: Boolean = false,
    val name: String = "",
    val description: String = "",
    val topics: ImmutableList<String> = persistentListOf(),
    val stargazersCount: Int = 0,
    val watchersCount: Int = 0,
    val forksCount: Int = 0,
    val owner: String = "",
    val avatarUrl: String = "",
    val followers: Int = 0,
    val following: Int = 0,
    val language: String = "",
    val publicRepos: Int = 0,
    val bio: String = "",
)