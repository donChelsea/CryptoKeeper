package com.example.cryptokeeper.presentation.screens.detail.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cryptokeeper.R
import com.example.cryptokeeper.domain.model.CoinDetail
import com.example.cryptokeeper.presentation.composables.CoinTag
import com.example.cryptokeeper.presentation.composables.ShowError
import com.example.cryptokeeper.presentation.composables.ShowLoading
import com.example.cryptokeeper.presentation.composables.TeamMemberListItem
import com.example.cryptokeeper.presentation.screens.detail.CoinDetailAction
import com.example.cryptokeeper.presentation.screens.detail.CoinDetailEvent
import com.example.cryptokeeper.presentation.screens.detail.CoinDetailState
import com.example.cryptokeeper.presentation.screens.detail.CoinDetailViewModel
import com.example.cryptokeeper.presentation.screens.detail.ScreenData
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun CoinDetailScreen(
    viewModel: CoinDetailViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.events.collect { event ->
            when (event) {
                is CoinDetailEvent.OnBackClicked -> navController.navigateUp()
            }
        }
    }

    CoinDetailLayout(
        state = state,
        onAction = viewModel::handleAction
    )
}


@Composable
private fun CoinDetailLayout(
    state: CoinDetailState,
    onAction: (CoinDetailAction) -> Unit,
) {
    when(state.screenData) {
        is ScreenData.Initial -> {}
        is ScreenData.Offline -> {}
        is ScreenData.Loading -> ShowLoading()
        is ScreenData.Error -> ShowError()
        is ScreenData.Data -> state.screenData.coin?.let {
            CoinDetailContent(coin = it)
        }
    }
}

@Composable
private fun CoinDetailContent(
    coin: CoinDetail
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.coin_list_item_title, coin.rank, coin.name, coin.symbol),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.weight(8f)
                    )
                    Text(
                        text = if (coin.isActive) stringResource(id = R.string.active) else stringResource(id = R.string.inactive),
                        color = if (coin.isActive) Color.Green else Color.Gray,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .align(CenterVertically)
                            .weight(2f)
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = coin.description,
                    style = MaterialTheme.typography.bodyMedium,
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = stringResource(id = R.string.tags),
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(15.dp))

                FlowRow(
                    mainAxisSpacing = 10.dp,
                    crossAxisSpacing = 10.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    coin.tags.forEach { tag ->
                        CoinTag(tag = tag)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(id = R.string.team_members),
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(15.dp))
            }
            items(coin.team) { teamMember ->
                TeamMemberListItem(
                    teamMember = teamMember,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                Divider()
            }
        }
    }
}
