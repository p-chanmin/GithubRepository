package com.dev.githubrepository.core.designsystem.component

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.dev.githubrepository.core.designsystem.R
import com.dev.githubrepository.core.designsystem.theme.GithubRepositoryTheme

@Composable
fun DefaultAsyncImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val context = LocalContext.current
    val imageLoader = LocalContext.current.imageLoader

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(data = imageUrl)
            .placeholder(R.drawable.ic_downloading_24)
            .error(R.drawable.ic_error_outline_24)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        imageLoader = imageLoader,
        modifier = modifier
    )
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
private fun DefaultAsyncImagePreview() {
    GithubRepositoryTheme {
        DefaultAsyncImage(
            modifier = Modifier,
            imageUrl = "",
        )
    }
}