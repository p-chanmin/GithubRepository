package com.dev.githubrepository.feature.repository.model

import androidx.compose.runtime.Immutable

@Immutable
data class RepositoryUiState(
    val owner: String = "",
    val repo: String = "",
)