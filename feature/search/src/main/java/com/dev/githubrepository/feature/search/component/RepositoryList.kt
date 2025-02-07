package com.dev.githubrepository.feature.search.component

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dev.githubrepository.core.designsystem.component.DefaultAsyncImage
import com.dev.githubrepository.core.designsystem.component.FormattedCountText
import com.dev.githubrepository.core.designsystem.language.languageColors
import com.dev.githubrepository.core.designsystem.theme.Black
import com.dev.githubrepository.core.designsystem.theme.GithubRepositoryTheme
import com.dev.githubrepository.core.designsystem.theme.Paddings
import com.dev.githubrepository.core.designsystem.theme.Yellow
import com.dev.githubrepository.core.model.RepositoryInfo
import com.dev.githubrepository.feature.search.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun RepositoryList(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    repositoryList: ImmutableList<RepositoryInfo>,
    onRepositoryClick: (String, String) -> Unit,
    loadMoreRepositories: (Int) -> Unit,
) {

    LaunchedEffect(Unit) {
        snapshotFlow { listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size }
            .collectLatest { currentItemIndex ->
                loadMoreRepositories(currentItemIndex)
            }
    }

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        itemsIndexed(repositoryList, key = { _, repo -> repo.id }) { i, repositoryInfo ->

            RepositoryItem(
                repositoryInfo = repositoryInfo,
                onRepositoryClick = onRepositoryClick,
            )

            if (i < repositoryList.size - 1) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun RepositoryItem(
    repositoryInfo: RepositoryInfo,
    onRepositoryClick: (String, String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onRepositoryClick(repositoryInfo.owner, repositoryInfo.name)
            }
            .padding(Paddings.large),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            DefaultAsyncImage(
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape),
                imageUrl = repositoryInfo.avatarUrl,
                contentDescription = repositoryInfo.owner
            )
            Text(
                modifier = Modifier.padding(start = Paddings.medium),
                text = repositoryInfo.owner,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Text(
            modifier = Modifier.padding(top = Paddings.small),
            text = repositoryInfo.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            modifier = Modifier,
            text = repositoryInfo.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Row(
            modifier = Modifier.padding(top = Paddings.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Filled.Star,
                contentDescription = stringResource(R.string.feature_search_ic_stargazerscount),
                tint = Yellow
            )
            FormattedCountText(
                modifier = Modifier.padding(start = Paddings.small),
                value = repositoryInfo.stargazersCount,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            repositoryInfo.language?.let { lang ->
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(start = Paddings.medium),
                    imageVector = Icons.Filled.Circle,
                    contentDescription = stringResource(R.string.feature_search_ic_language),
                    tint = languageColors.getOrDefault(lang, Black)
                )
                Text(
                    modifier = Modifier.padding(start = Paddings.small),
                    text = lang,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
private fun RepositoryItemPreview() {
    GithubRepositoryTheme {
        RepositoryItem(
            repositoryInfo = RepositoryInfo(
                id = 1,
                name = "Android",
                language = "Kotlin",
                stargazersCount = 3200,
                description = "description",
                topics = listOf("123", "23", "42"),
                owner = "open-android",
                avatarUrl = ""
            ),
            onRepositoryClick = { _, _ -> }
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
private fun RepositoryListPreview() {
    GithubRepositoryTheme {
        RepositoryList(
            modifier = Modifier.fillMaxSize(),
            repositoryList = persistentListOf(
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
                RepositoryInfo(
                    id = 2,
                    name = "Android",
                    language = "Java",
                    stargazersCount = 34567,
                    watchersCount = 1,
                    forksCount = 42,
                    description = "description",
                    topics = listOf("123", "23", "42"),
                    owner = "open-android",
                    avatarUrl = ""
                ),
                RepositoryInfo(
                    id = 3,
                    name = "Android",
                    language = "ASL",
                    stargazersCount = 12356,
                    watchersCount = 1,
                    forksCount = 42,
                    description = "description",
                    topics = listOf("123", "23", "42"),
                    owner = "open-android",
                    avatarUrl = ""
                ),
            ),
            onRepositoryClick = { _, _ -> },
            loadMoreRepositories = { }
        )
    }
}