package com.example.test.flights.presentation.segmented_tab

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SegmentedSection() {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 32.dp)
    ) {
        SegmentedControl(
            options = listOf("Booked", "History"),
            selectedIndex = selectedIndex,
            onOptionSelected = { selectedIndex = it }
        )
    }
}

@Composable
fun SegmentedControl(
    options: List<String>,
    selectedIndex: Int,
    onOptionSelected: (Int) -> Unit
) {
    val padding = 4.dp

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val innerWidth = maxWidth - (padding * 2)
        val tabWidth = innerWidth / options.size    // chia đều theo số tab

        val indicatorOffset by animateDpAsState(
            targetValue = tabWidth * selectedIndex + padding,
            animationSpec = tween(durationMillis = 200),
            label = "indicatorOffset"
        )

        // Container
        Box(
            modifier = Modifier
                .width(innerWidth + padding * 2)
                .height(52.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFF5F5F5))
                .padding(padding)
        ) {
            // Indicator
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .width(tabWidth)
                    .fillMaxHeight()
                    .shadow(2.dp, RoundedCornerShape(50))
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
            )

            // Tabs
            Row(
                Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                options.forEachIndexed { index, option ->
                    Text(
                        text = option,
                        modifier = Modifier
                            .weight(1f) // mỗi tab chia đều
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                onOptionSelected(index)
                            },
                        textAlign = TextAlign.Center,
                        color = if (index == selectedIndex) Color.Black else Color.Gray
                    )
                }
            }
        }
    }
}