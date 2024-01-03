package com.john.buscalibre.shared.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.john.buscalibre.R

@Composable
fun HeaderSearchView(hasBack: Boolean, navigationController: NavHostController, onSearch: (String) -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
    ) {
        val (arrowBack, searchBox) = createRefs()
        if (hasBack) {
            IconButton(onClick = {
                navigationController.popBackStack()
            }, modifier = Modifier.constrainAs(arrowBack) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back",
                    tint = Color.Black
                )
            }
        }
        Box(modifier = Modifier.constrainAs(searchBox) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) { SearchBox(onSearch) }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchBox(onSearch: (String) -> Unit) {
    var query by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = query,
        onValueChange = { query = it },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary),
        label = { Text(stringResource(R.string.searching_in_free_search)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
            )
        },
        shape = MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(30.dp),
            topEnd = CornerSize(30.dp),
            bottomStart = CornerSize(30.dp),
            bottomEnd = CornerSize(30.dp)
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                if (query.isNotEmpty()) {
                    onSearch.invoke(query)
                }
                query = ""
            }
        )
    )
}
