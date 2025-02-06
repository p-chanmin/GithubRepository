package com.dev.githubrepository.core.designsystem.component

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.dev.githubrepository.core.designsystem.theme.GithubRepositoryTheme

@Composable
fun FormattedCountText(
    modifier: Modifier = Modifier,
    value: Int,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    val formattedValue = when {
        value >= 1000000 -> "%.1fm".format(value / 1000000.0)
        value >= 1000 -> "%.1fk".format(value / 1000.0)
        else -> "$value"
    }

    Text(
        modifier = modifier,
        text = formattedValue,
        style = style,
        color = color,
    )
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
private fun FormattedCountTextPreview() {
    GithubRepositoryTheme {
        FormattedCountText(
            value = 3200,
        )
    }
}