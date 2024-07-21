package com.example.cryptokeeper.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ShowLoading() {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ShowLoadingListWithShimmer() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        contentPadding = PaddingValues(20.dp)
    ) {
        items(count = 8) {
            AnimatedShimmer(isList = true)
        }
    }
}

@Composable
fun ShowLoadingCoinDetailWithShimmer() {
    AnimatedShimmer(isList = false)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewShowLoading() {
    ShowLoading()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewShowLoadingListWithShimmer() {
    AnimatedShimmer(isList = true)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewShowLoadingCoinDetailWithShimmer() {
    AnimatedShimmer(isList = false)
}