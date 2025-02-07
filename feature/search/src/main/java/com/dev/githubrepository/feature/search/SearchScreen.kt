package com.dev.githubrepository.feature.search

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dev.githubrepository.core.designsystem.theme.GithubRepositoryTheme
import com.dev.githubrepository.core.designsystem.theme.Paddings
import com.dev.githubrepository.core.model.RepositoryInfo
import com.dev.githubrepository.feature.search.component.RepositoryList
import com.dev.githubrepository.feature.search.component.SearchTextField
import com.dev.githubrepository.feature.search.model.SearchUiState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SearchScreen(
    paddingValues: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    navigateToRepository: (String, String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchUiState by viewModel.searchUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.errorFlow.collectLatest { throwable -> onShowErrorSnackBar(throwable) }
    }

    SearchContent(
        searchUiState = searchUiState,
        paddingValues = paddingValues,
        navigateToRepository = navigateToRepository,
        onKeywordChange = viewModel::changeKeyword,
        onSearchClick = viewModel::searchKeyword,
        loadMoreRepositories = viewModel::loadMoreRepositories,
    )
}

@Composable
private fun SearchContent(
    searchUiState: SearchUiState,
    paddingValues: PaddingValues,
    navigateToRepository: (String, String) -> Unit,
    onKeywordChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    loadMoreRepositories: (Int, Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SearchTextField(
            modifier = Modifier
                .padding(top = Paddings.medium)
                .fillMaxWidth(),
            value = searchUiState.keyword,
            onValueChange = onKeywordChange,
            onSearchClick = onSearchClick,
            placeholderText = stringResource(R.string.feature_search_search_text_field_placeholder)
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            RepositoryList(
                modifier = Modifier.fillMaxSize(),
                repositoryList = searchUiState.repositories,
                onRepositoryClick = navigateToRepository,
                loadMoreRepositories = loadMoreRepositories,
            )
            if (searchUiState.showProgress) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary)
            }
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
private fun SearchContentPreview() {
    GithubRepositoryTheme {
        SearchContent(
            searchUiState = SearchUiState(
                keyword = "123",
                showProgress = true,
                repositories = persistentListOf(
                    RepositoryInfo(
                        id = 1,
                        name = "Android",
                        language = "Kotlin",
                        stargazersCount = 5342,
                        watchersCount = 1,
                        forksCount = 42,
                        description = "description",
                        topics = listOf("123", "23", "42"),
                        owner = "open-android",
                        avatarUrl = ""
                    ),
                )
            ),
            paddingValues = PaddingValues(),
            navigateToRepository = { _, _ -> },
            onKeywordChange = {},
            onSearchClick = {},
            loadMoreRepositories = { _, _ -> },
        )
    }
}