@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cryptokeeper.presentation.screens.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cryptokeeper.R
import com.example.cryptokeeper.presentation.composables.CoinListItem
import com.example.cryptokeeper.presentation.composables.ShowError
import com.example.cryptokeeper.presentation.composables.ShowOffline
import com.example.cryptokeeper.presentation.navigation.NavScreen
import com.example.cryptokeeper.presentation.screens.search.ScreenData
import com.example.cryptokeeper.presentation.screens.search.SearchAction
import com.example.cryptokeeper.presentation.screens.search.SearchEvent
import com.example.cryptokeeper.presentation.screens.search.SearchState
import com.example.cryptokeeper.presentation.screens.search.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.events.collect { event ->
            when (event) {
                is SearchEvent.OnCoinClicked -> navController.navigate(
                    NavScreen.CoinDetail.withArgs(event.coinId, event.coinName)
                )
                SearchEvent.OnSearchClicked -> TODO()
            }
        }
    }

    SearchLayout(
        state = state,
        searchHistory = viewModel.searchHistory,
        onAction = viewModel::handleAction,
    )
}

@Composable
private fun SearchLayout(
    state: SearchState,
    searchHistory: Set<String>,
    onAction: (SearchAction) -> Unit,
) {
    when (state.screenData) {
        is ScreenData.Initial -> {}
        is ScreenData.Offline -> ShowOffline()
        is ScreenData.Loading -> LoadingSearchContent(
            searchHistory = searchHistory,
            onAction = onAction,
        )
        is ScreenData.Error -> ShowError()
        is ScreenData.Data -> SearchContent(
            state = state,
            onItemClick = { id, name ->
                onAction(
                    SearchAction.OnCoinClicked(
                        coinId = id,
                        coinName = name
                    )
                )
            },
        )
    }
}

@Composable
private fun SearchContent(
    state: SearchState,
    onItemClick: (String, String) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items((state.screenData as ScreenData.Data).results) { coin ->
            CoinListItem(
                coin = coin,
                onItemClick = { id, name -> onItemClick(id, name) }
            )
        }
    }
}

@Composable
private fun LoadingSearchContent(
    onAction: (SearchAction) -> Unit,
    searchHistory: Set<String>,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        var query by remember { mutableStateOf("") }
        var active by remember { mutableStateOf(false) }

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            query = query,
            onQueryChange = { query = it },
            onSearch = {
                if (!searchHistory.contains(it.trim())) {
                    onAction(SearchAction.OnSearchClicked(it.trim()))
                }
                active = false
                query = ""
            },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text(text = stringResource(id = R.string.search_placeholder)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search)
                )
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.close),
                        modifier = Modifier.clickable {
                            if (query.isNotEmpty()) {
                                query = ""
                            } else {
                                active = false
                            }
                        }
                    )
                }
            }
        ) {
            searchHistory.forEach { item ->
                Row(
                    modifier = Modifier
                        .padding(14.dp)
                        .clickable { onAction(SearchAction.OnSearchHistoryItemClicked(item)) },
                    verticalAlignment = CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        imageVector = Icons.Default.History,
                        contentDescription = stringResource(id = R.string.history_icon),
                    )
                    Text(text = item)
                }
            }
        }
    }
}