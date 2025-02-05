package com.dev.githubrepository.feature.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dev.githubrepository.core.designsystem.theme.GithubRepositoryTheme
import com.dev.githubrepository.feature.search.model.SearchUiState
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
    )
}

@Composable
private fun SearchContent(
    searchUiState: SearchUiState,
    paddingValues: PaddingValues,
    navigateToRepository: (String, String) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Text(
            text = "SearchContent"
        )
        Button(
            onClick = { navigateToRepository("test owner1", "test repo1") }
        ) { }
        Button(
            onClick = { navigateToRepository("test owner2", "test repo2") }
        ) { }
        Button(
            onClick = { navigateToRepository("test owner3", "test repo3") }
        ) { }
        Button(
            onClick = { navigateToRepository("test owner4", "test repo4") }
        ) { }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SearchContentPreview() {
    GithubRepositoryTheme {
        SearchContent(
            searchUiState = SearchUiState(),
            paddingValues = PaddingValues(),
            navigateToRepository = { _, _ -> },
        )
    }
}