package com.dev.githubrepository.feature.repository.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dev.githubrepository.core.designsystem.component.DefaultAsyncImage
import com.dev.githubrepository.core.designsystem.theme.GithubRepositoryTheme
import com.dev.githubrepository.core.designsystem.theme.Paddings
import com.dev.githubrepository.feature.repository.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UserBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    username: String,
    avatarUrl: String,
    followers: Int,
    following: Int,
    language: String,
    publicRepos: Int,
    bio: String,
    closeSheet: () -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = closeSheet,
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Paddings.xlarge, vertical = Paddings.extra),
            verticalArrangement = Arrangement.spacedBy(Paddings.xlarge)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DefaultAsyncImage(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    imageUrl = avatarUrl,
                    contentDescription = username
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = Paddings.large)
                        .weight(1f),
                    text = username,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Paddings.medium, top = Paddings.medium),
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.feature_repository_followers),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    modifier = Modifier.padding(start = Paddings.medium),
                    text = followers.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Paddings.medium),
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.feature_repository_following),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    modifier = Modifier.padding(start = Paddings.medium),
                    text = following.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Paddings.medium),
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.feature_repository_language),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    modifier = Modifier.padding(start = Paddings.medium),
                    text = language,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Paddings.medium),
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.feature_repository_repositories),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    modifier = Modifier.padding(start = Paddings.medium),
                    text = publicRepos.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Paddings.medium),
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.feature_repository_bio),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    modifier = Modifier.padding(start = Paddings.medium),
                    text = bio,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
private fun UserBottomSheetPreview() {
    GithubRepositoryTheme {
        val sheetState = rememberStandardBottomSheetState()

        UserBottomSheet(
            modifier = Modifier,
            sheetState = sheetState,
            username = "user1",
            avatarUrl = "",
            followers = 100,
            following = 200,
            language = "lang, lang, lang, lang, lang, lang, lang, lang, lang, lang",
            bio = "test bio test bio test bio test bio test bio test bio test bio ",
            publicRepos = 124,
            closeSheet = {},
        )
    }
}