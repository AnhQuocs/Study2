package com.example.test.shimmer_loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test.multiple_lang.AppLanguage
import com.example.test.multiple_lang.Hotel
import com.example.test.multiple_lang.localized
import com.example.test.multiple_lang.presentation.viewmodel.HotelViewModel
import androidx.compose.foundation.lazy.items
import coil.compose.SubcomposeAsyncImage

@Composable
fun HotelCard(viewModel: HotelViewModel) {
    val hotels by viewModel.hotels.collectAsState()
    val language by viewModel.language.collectAsState()

    var showCardLoading by remember { mutableStateOf(true) }

    LaunchedEffect(hotels) {
        showCardLoading = hotels.isEmpty()
    }

    if(showCardLoading || hotels.isEmpty()) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(8) {
                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .padding(top = 16.dp)
                        .height(300.dp)
                        .width(200.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    ShimmerLoadingAnimation()
                }
            }
        }

    } else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(hotels) {hotel ->
                HotelCardItem(hotel, language)
            }
        }
    }
}

@Composable
fun HotelCardItem(hotel: Hotel, language: AppLanguage) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(vertical = 16.dp)
            .padding(top = 16.dp)
            .height(300.dp)
            .width(200.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            SubcomposeAsyncImage(
                model = hotel.thumbnailUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                loading = {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray.copy(alpha = 0.3f))) // hiệu ứng loading
                },
                error = {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red)) // nếu lỗi load ảnh
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .padding(bottom = 8.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = hotel.name.localized(language.code),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = hotel.shortAddress,
                    fontSize = 16.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "$" + hotel.pricePerNightMin.toString() + "/night",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }

            Text(
                text = "⭐" + hotel.averageRating,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .padding(top = 12.dp, end = 12.dp)
                    .align(Alignment.TopEnd)
                    .size(35.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                var isFavorite by remember { mutableStateOf(false) }

                IconButton(
                    onClick = { isFavorite = !isFavorite }
                ) {
                    if(isFavorite) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    } else {
                        Icon(
                            Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }
}