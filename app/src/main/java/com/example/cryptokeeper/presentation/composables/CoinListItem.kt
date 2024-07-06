package com.example.cryptokeeper.presentation.composables

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptokeeper.R
import com.example.cryptokeeper.domain.model.Coin
import com.example.cryptokeeper.domain.model.TeamMember

@Composable
fun CoinListItem(
    coin: Coin,
    onItemClick: (String) -> Unit,
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick(coin.id) }
        .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.coin_list_item_title, coin.rank, coin.name, coin.symbol),
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = if (coin.isActive) stringResource(id = R.string.active) else stringResource(id = R.string.inactive),
            color = if (coin.isActive) Color.Green else Color.Gray,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(CenterVertically)
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewCoinListItem() {
    CoinListItem(
        coin = Coin(
            id = "Id",
            isActive = false,
            name = "Name",
            rank = 1,
            symbol = "SYM",
            type = "Type"
        ),
        onItemClick = {}
    )
}