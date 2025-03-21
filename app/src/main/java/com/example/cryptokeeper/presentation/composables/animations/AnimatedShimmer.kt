package com.example.cryptokeeper.presentation.composables.animations

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedShimmer(isList: Boolean) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        ),
        label = "",
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(
            x = translateAnim.value,
            y = translateAnim.value
        )
    )

    if (isList) {
        ShimmerGridItem(brush = brush)
    } else {
        ShimmerCoinDetail(brush = brush)
    }
}

@Composable
private fun ShimmerGridItem(brush: Brush) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 40.dp)
                .requiredHeight(60.dp)
                .background(brush)
                .clip(RoundedCornerShape(CornerSize(20.dp)))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Spacer(
            modifier = Modifier
                .requiredWidth(40.dp)
                .requiredHeight(40.dp)
                .background(brush)
                .clip(RoundedCornerShape(CornerSize(20.dp)))
        )
    }
}

@Composable
private fun ShimmerCoinDetail(brush: Brush) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 40.dp)
                    .requiredHeight(60.dp)
                    .background(brush)
                    .clip(RoundedCornerShape(CornerSize(20.dp)))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Spacer(
                modifier = Modifier
                    .requiredWidth(40.dp)
                    .requiredHeight(40.dp)
                    .background(brush)
                    .clip(RoundedCornerShape(CornerSize(20.dp)))
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(80.dp)
                .padding(horizontal = 20.dp)
                .background(brush)
                .clip(RoundedCornerShape(CornerSize(20.dp)))
        )
        Spacer(
            modifier = Modifier
                .requiredWidth(100.dp)
                .padding(start = 20.dp, top = 12.dp)
                .requiredHeight(40.dp)
                .background(brush)
                .clip(RoundedCornerShape(CornerSize(20.dp)))
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(80.dp)
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .background(brush)
                .clip(RoundedCornerShape(CornerSize(20.dp)))
        )
        Spacer(
            modifier = Modifier
                .requiredWidth(140.dp)
                .padding(start = 20.dp, top = 12.dp)
                .requiredHeight(40.dp)
                .background(brush)
                .clip(RoundedCornerShape(CornerSize(20.dp)))
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(300.dp)
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .background(brush)
                .clip(RoundedCornerShape(CornerSize(20.dp)))
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ShimmerGridItemPreview() {
    ShimmerGridItem(
        brush = Brush.linearGradient(
            colors = listOf(
                Color.LightGray.copy(alpha = 0.6f),
                Color.LightGray.copy(alpha = 0.2f),
                Color.LightGray.copy(alpha = 0.6f),
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun ShimmerCoinDetailPreview() {
    ShimmerCoinDetail(
        brush = Brush.linearGradient(
            colors = listOf(
                Color.LightGray.copy(alpha = 0.6f),
                Color.LightGray.copy(alpha = 0.2f),
                Color.LightGray.copy(alpha = 0.6f),
            )
        )
    )
}
