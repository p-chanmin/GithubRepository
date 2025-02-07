package com.dev.githubrepository.feature.repository

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dev.githubrepository.core.designsystem.theme.GithubRepositoryTheme
import com.dev.githubrepository.core.designsystem.theme.Paddings
import com.dev.githubrepository.feature.repository.component.RepositoryCountItems
import com.dev.githubrepository.feature.repository.component.RepositoryOwnerItem
import com.dev.githubrepository.feature.repository.component.TopicText
import com.dev.githubrepository.feature.repository.component.UserBottomSheet
import com.dev.githubrepository.feature.repository.model.RepositoryUiState
import kotlinx.collections.immutable.persistentListOf
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
        updateBottomSheetState = viewModel::updateBottomSheetState,
        refreshRepositoryDetail = viewModel::refreshRepositoryDetail,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RepositoryContent(
    repositoryUiState: RepositoryUiState,
    paddingValues: PaddingValues,
    updateBottomSheetState: (Boolean) -> Unit,
    refreshRepositoryDetail: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val sheetState = rememberModalBottomSheetState()

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(Paddings.large)
        ) {
            Text(
                modifier = Modifier.padding(top = Paddings.extra),
                text = repositoryUiState.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
            )
            LazyRow(
                modifier = Modifier.padding(top = Paddings.medium),
                horizontalArrangement = Arrangement.spacedBy(Paddings.small)
            ) {
                items(repositoryUiState.topics, key = { it }) {
                    TopicText(
                        topic = it,
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(top = Paddings.extra))

            RepositoryCountItems(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Paddings.extra),
                stargazersCount = repositoryUiState.stargazersCount,
                watchersCount = repositoryUiState.watchersCount,
                forksCount = repositoryUiState.forksCount
            )

            HorizontalDivider(modifier = Modifier)

            RepositoryOwnerItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Paddings.extra),
                owner = repositoryUiState.owner,
                avatarUrl = repositoryUiState.avatarUrl,
                onMoreClick = { updateBottomSheetState(true) }
            )

            HorizontalDivider(modifier = Modifier)

            Text(
                modifier = Modifier.padding(top = Paddings.xlarge),
                text = stringResource(R.string.feature_repository_description),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Text(
                modifier = Modifier.padding(top = Paddings.medium),
                text = repositoryUiState.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        IconButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = { refreshRepositoryDetail() },
        ) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = stringResource(R.string.feature_repository_ic_refresh),
            )
        }

        if (repositoryUiState.showBottomSheet) {
            UserBottomSheet(
                sheetState = sheetState,
                username = repositoryUiState.owner,
                avatarUrl = repositoryUiState.avatarUrl,
                followers = repositoryUiState.followers,
                following = repositoryUiState.following,
                language = repositoryUiState.language,
                bio = repositoryUiState.bio,
                publicRepos = repositoryUiState.publicRepos,
                closeSheet = { updateBottomSheetState(false) }
            )
        }

        if (repositoryUiState.showProgress) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary)
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
private fun RepositoryContentPreview() {
    GithubRepositoryTheme {
        RepositoryContent(
            repositoryUiState = RepositoryUiState(
                showProgress = false,
                showBottomSheet = false,
                name = "android",
                description = ":phone: The ownCloud Android app",
                topics = persistentListOf("android", "kotlin", "owncloud"),
                stargazersCount = 3943,
                watchersCount = 3942,
                forksCount = 3178,
                owner = "owncloud",
                avatarUrl = "",
                followers = 954,
                following = 213,
                language = "PHP, Shell, Kotlin, Java",
                publicRepos = 168,
                bio = "asdfasdfasdf",
            ),
            paddingValues = PaddingValues(),
            updateBottomSheetState = {},
            refreshRepositoryDetail = {}
        )
    }
}