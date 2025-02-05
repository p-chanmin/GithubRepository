package com.dev.githubrepository.feature.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.dev.githubrepository.feature.repository.navigation.repositoryNavGraph
import com.dev.githubrepository.feature.search.navigation.searchNavGraph

@Composable
internal fun MainNavHost(
    navigator: MainNavigator,
    paddingValues: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        searchNavGraph(
            paddingValues = paddingValues,
            onShowErrorSnackBar = onShowErrorSnackBar,
            navigateToRepository = { owner, repo -> navigator.navigateToRepository(owner, repo) }
        )
        repositoryNavGraph(
            paddingValues = paddingValues,
            onShowErrorSnackBar = onShowErrorSnackBar,
        )
    }
}