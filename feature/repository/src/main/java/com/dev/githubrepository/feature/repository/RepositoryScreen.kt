package com.dev.githubrepository.feature.repository

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dev.githubrepository.core.designsystem.theme.GithubRepositoryTheme
import com.dev.githubrepository.feature.repository.model.RepositoryUiState
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun RepositoryScreen(
    paddingValues: PaddingValues,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    viewModel: RepositoryViewModel = hiltViewModel()
) {
    val repositoryUiState by viewModel.repositoryUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.errorFlow.collectLatest { throwable -> onShowErrorSnackBar(throwable) }
    }

    RepositoryContent(
        repositoryUiState = repositoryUiState,
        paddingValues = paddingValues,
    )
}

@Composable
private fun RepositoryContent(
    repositoryUiState: RepositoryUiState,
    paddingValues: PaddingValues,
) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Text(
            text = "RepositoryContent ${repositoryUiState.owner}, ${repositoryUiState.repo}"
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun RepositoryContentPreview() {
    GithubRepositoryTheme {
        RepositoryContent(
            repositoryUiState = RepositoryUiState(),
            paddingValues = PaddingValues(),
        )
    }
}