package com.dev.githubrepository.feature.repository.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

@Composable
internal fun RepositoryOwnerItem(
    modifier: Modifier = Modifier,
    owner: String,
    avatarUrl: String,
    onMoreClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DefaultAsyncImage(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            imageUrl = avatarUrl,
            contentDescription = owner
        )
        Text(
            modifier = Modifier
                .padding(horizontal = Paddings.medium)
                .weight(1f),
            text = owner,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Button(
            modifier = Modifier,
            colors = ButtonColors(
                contentColor = MaterialTheme.colorScheme.onTertiary,
                containerColor = MaterialTheme.colorScheme.tertiary,
                disabledContentColor = MaterialTheme.colorScheme.onTertiary,
                disabledContainerColor = MaterialTheme.colorScheme.scrim,
            ),
            onClick = onMoreClick
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(R.string.feature_repository_more),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
private fun RepositoryOwnerItemPreview() {
    GithubRepositoryTheme {
        RepositoryOwnerItem(
            modifier = Modifier.fillMaxWidth(),
            owner = "owner",
            avatarUrl = "",
            onMoreClick = {}
        )
    }
}