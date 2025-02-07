package com.dev.githubrepository.feature.search.model

import androidx.compose.runtime.Immutable
import com.dev.githubrepository.core.model.RepositoryInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class SearchUiState(
    val keyword: String = "",
    val repositories: ImmutableList<RepositoryInfo> = persistentListOf(),
    val showProgress: Boolean = false
)