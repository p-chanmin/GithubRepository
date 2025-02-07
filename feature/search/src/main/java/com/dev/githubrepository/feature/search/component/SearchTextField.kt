package com.dev.githubrepository.feature.search.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.dev.githubrepository.core.designsystem.component.DefaultTextField
import com.dev.githubrepository.core.designsystem.theme.GithubRepositoryTheme
import com.dev.githubrepository.core.designsystem.theme.Paddings
import com.dev.githubrepository.feature.search.R

@Composable
internal fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    placeholderText: String,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DefaultTextField(
            modifier = Modifier
                .padding(start = Paddings.medium)
                .weight(1f),
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onSearchClick()
                },
            ),
            singleLine = true,
            placeholderText = placeholderText
        )
        IconButton(
            onClick = {
                focusManager.clearFocus()
                keyboardController?.hide()
                onSearchClick()
            },
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(R.string.feature_search_ic_search),
            )
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
private fun SearchTextFieldPreview() {
    GithubRepositoryTheme {
        SearchTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "test",
            onValueChange = {},
            onSearchClick = {},
            placeholderText = "placeholderText"
        )
    }
}