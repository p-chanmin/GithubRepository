package com.dev.githubrepository.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Search : Route

    @Serializable
    data class Repository(val owner: String, val repo: String) : Route
}