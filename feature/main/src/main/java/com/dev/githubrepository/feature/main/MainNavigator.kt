package com.dev.githubrepository.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dev.githubrepository.core.navigation.Route
import com.dev.githubrepository.feature.repository.navigation.navigateToRepository
import com.dev.githubrepository.feature.search.navigation.navigateToSearch

internal class MainNavigator(
    val navController: NavHostController,
) {
    val startDestination = Route.Search

    fun navigateToSearch() {
        navController.navigateToSearch()
    }

    fun navigateToRepository(owner: String, repo: String) {
        navController.navigateToRepository(owner, repo)
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}