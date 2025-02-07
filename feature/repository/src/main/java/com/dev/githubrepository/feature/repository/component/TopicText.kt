package com.dev.githubrepository.feature.repository.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dev.githubrepository.core.designsystem.theme.GithubRepositoryTheme
import com.dev.githubrepository.core.designsystem.theme.Paddings

@Composable
internal fun TopicText(
    modifier: Modifier = Modifier,
    topic: String,
    textColor: Color = MaterialTheme.colorScheme.secondary,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
) {
    Text(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(50.dp)
            )
            .padding(vertical = Paddings.small, horizontal = Paddings.large),
        text = topic,
        style = style,
        color = textColor,
    )
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
private fun TopicTextPreview() {
    GithubRepositoryTheme {
        TopicText(
            modifier = Modifier,
            topic = "android",
        )
    }
}