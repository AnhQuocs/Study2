package com.example.test.multiple_lang.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.test.multiple_lang.AppLanguage
import com.example.test.multiple_lang.Hotel
import com.example.test.multiple_lang.localized
import com.example.test.multiple_lang.presentation.viewmodel.HotelViewModel
import com.example.test.shimmer_loading.HotelCard
import com.example.test.weather.presentation.ui.WeatherSection

@Composable
fun HotelList(viewModel: HotelViewModel) {
    val hotels by viewModel.hotels.collectAsState()
    val language by viewModel.language.collectAsState()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(top = 32.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp)
        ) {
            Button(
                onClick = {
                    viewModel.changeLanguage(AppLanguage.EN)
                }
            ) {
                Text("English")
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = {
                    viewModel.changeLanguage(AppLanguage.VI)
                }
            ) {
                Text("Tiếng Việt")
            }
        }

        WeatherSection(context = context)

        HotelCard(viewModel)
    }
}

@Composable
fun HotelItem(hotel: Hotel, language: AppLanguage) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = hotel.thumbnailUrl,
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
        )

        Text(
            text = hotel.name.localized(language.code),
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = hotel.shortAddress,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black.copy(alpha = 0.8f)
            )

            Text(
                text = " • " + "⭐" + hotel.averageRating
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "• " + hotel.description.localized(language.code),
            fontSize = 16.sp
        )
    }
}

/*
Text(hotel.name.localized(language.code))
Text(hotel.description.localized(language.code))
hotel.amenities.localizedList(language.code).forEach {
    Text("• $it")
}*/