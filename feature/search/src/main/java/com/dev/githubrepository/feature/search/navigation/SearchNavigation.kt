package com.dev.githubrepository.feature.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dev.githubrepository.core.navigation.Route
import com.dev.githubrepository.feature.search.SearchScreen

fun NavController.navigateToSearch() {
    navigate(Route.Search)
}

fun NavGraphBuilder.searchNavGraph(
    paddingValues: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    navigateToRepository: (String, String) -> Unit,
) {
    composable<Route.Search> {
        SearchScreen(
            paddingValues = paddingValues,
            onShowErrorSnackBar = onShowErrorSnackBar,
            navigateToRepository = navigateToRepository
        )
    }
}