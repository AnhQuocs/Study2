package com.example.test.indicator_tab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TabScreen() {
    var currentTab by remember { mutableIntStateOf(0) }
    val interactionSource = remember { MutableInteractionSource() }
    val tabs = listOf("Hotel", "Flight")
    val animationDuration = 120

    Scaffold(
        topBar = {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(Icons.Default.Home, contentDescription = null)
                Icon(Icons.Default.FavoriteBorder, contentDescription = null)
                Icon(Icons.Default.Person, contentDescription = null)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(top = 16.dp)
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Divider trên tab
            Divider(color = Color.LightGray, thickness = 1.dp)

            Box(
            ) {
                TabRow(
                    selectedTabIndex = currentTab,
                    containerColor = Color.White,
                    indicator = { tabPositions ->
                        Box(
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[currentTab])
                                .height(2.5.dp)
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        listOf(Color(0xFF7F7FD5), Color(0xFF86A8E7))
                                    ),
                                    shape = RoundedCornerShape(30)
                                )
                        )
                    },
                    divider = {
//                        Divider(color = Color.LightGray, thickness = 2.dp)
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Box(
                            modifier = Modifier
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) { currentTab = index }
                                .background(if (currentTab == index) Color.White else Color.LightGray.copy(alpha = 0.05f))
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                title,
                                color = if (currentTab == index) Color.Black else Color.Gray,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }

                // Thanh dọc ngăn 2 tab
//                Box(
//                    modifier = Modifier
//                        .height(38.dp)
//                        .width(1.dp)
//                        .background(Color.Black.copy(alpha = 0.4f))
//                        .align(Alignment.Center)
//                )
            }

            Divider(color = Color.LightGray, thickness = 1.dp)

            // Content
            Box(Modifier.fillMaxSize()) {
                this@Column.AnimatedVisibility(
                    visible = currentTab == 0,
                    enter = slideInHorizontally(
                        animationSpec = tween(durationMillis = animationDuration)
                    ) { -it } + fadeIn(tween(animationDuration)),
                    exit = slideOutHorizontally(
                        animationSpec = tween(durationMillis = animationDuration)
                    ) { -it } + fadeOut(tween(animationDuration))
                ) {
                    HotelScreenContent()
                }

                this@Column.AnimatedVisibility(
                    visible = currentTab == 1,
                    enter = slideInHorizontally(
                        animationSpec = tween(durationMillis = animationDuration)
                    ) { it } + fadeIn(tween(animationDuration)),
                    exit = slideOutHorizontally(
                        animationSpec = tween(durationMillis = animationDuration)
                    ) { it } + fadeOut(tween(animationDuration))
                ) {
                    FlightScreenContent()
                }
            }
        }
    }
}

@Composable
fun HotelScreenContent() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(color = Color.White)
    ) {
        items(20) { index ->
            Text(
                text = "Hotel item $index",
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun FlightScreenContent() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(color = Color.White)
    ) {
        items(20) { index ->
            Text(
                text = "Flight item $index",
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}