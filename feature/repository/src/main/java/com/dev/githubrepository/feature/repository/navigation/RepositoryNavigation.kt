package com.dev.githubrepository.feature.repository.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dev.githubrepository.core.navigation.Route
import com.dev.githubrepository.feature.repository.RepositoryScreen

fun NavController.navigateToRepository(owner: String, repo: String) {
    navigate(Route.Repository(owner, repo))
}

fun NavGraphBuilder.repositoryNavGraph(
    paddingValues: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    composable<Route.Repository>(
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
    ) {
        RepositoryScreen(
            paddingValues = paddingValues,
            onShowErrorSnackBar = onShowErrorSnackBar,
        )
    }
}