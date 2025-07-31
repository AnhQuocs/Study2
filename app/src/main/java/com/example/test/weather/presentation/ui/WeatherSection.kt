package com.example.test.weather.presentation.ui

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.test.R
import com.example.test.weather.const.Const.permissions
import com.example.test.weather.domain.model.WeatherResult
import com.example.test.weather.presentation.viewmodel.STATE
import com.example.test.weather.presentation.viewmodel.WeatherViewModel
import com.example.test.weather.utils.Utils.buildIcon
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

@Composable
fun WeatherSection(viewModel: WeatherViewModel = hiltViewModel(), context: Context) {
    val fusedClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var locationGranted by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        locationGranted = it.values.all { granted -> granted }
    }

    LaunchedEffect(Unit) {
        if (permissions.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }) {
            locationGranted = true
        } else {
            launcher.launch(permissions)
        }
    }

    LaunchedEffect(locationGranted) {
        if (locationGranted) {
            fusedClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location ->
                    location?.let {
                        viewModel.getWeather(it.latitude, it.longitude)
                    }
                }
        }
    }

    when (viewModel.state) {
        STATE.LOADING -> LoadingCard()
        STATE.FAILED -> ErrorCard()
        STATE.SUCCESS -> viewModel.weather?.let {
            WeatherCard(it)
        }
    }
}

@Composable
fun WeatherCard(weather: WeatherResult) {
    val gradient = Brush.linearGradient(
        colors = listOf(Color(0xFFB2FEFA), Color(0xFF0ED2F7)),
        start = Offset(1000f, -1000f),
        end = Offset(1000f, 1000f)
    )

    Box(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(150.dp)
            .background(brush = gradient, shape = RoundedCornerShape(8.dp))
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .align(Alignment.TopStart),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = buildIcon(weather.weather?.firstOrNull()?.icon ?: ""),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.CenterStart)
                    )

                    Text(
                        text = weather.weather?.firstOrNull()?.description
                            ?.replaceFirstChar { it.titlecase() }
                            ?: "",
                        fontSize = 18.sp,
                        lineHeight = 1.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.3f),
                                offset = Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        ),
                        modifier = Modifier.align(Alignment.BottomStart)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .align(Alignment.TopEnd),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "${weather.main?.temp ?: "--"}°C",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.3f),
                                offset = Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        )
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = weather.name ?: "",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.3f),
                                offset = Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingCard() {
    val gradient = Brush.linearGradient(
        colors = listOf(Color(0xFFB2FEFA), Color(0xFF0ED2F7)),
        start = Offset(1000f, -1000f),
        end = Offset(1000f, 1000f)
    )

    Box(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(150.dp)
            .background(brush = gradient, shape = RoundedCornerShape(8.dp))
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White
                )
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun ErrorCard() {
    val gradient = Brush.linearGradient(
        colors = listOf(Color(0xFFB2FEFA), Color(0xFF0ED2F7)),
        start = Offset(1000f, -1000f),
        end = Offset(1000f, 1000f)
    )

    Box(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(150.dp)
            .background(brush = gradient, shape = RoundedCornerShape(8.dp))
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudOff,
                        contentDescription = null,
                        modifier = Modifier.size(68.dp),
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = stringResource(id = R.string.error),
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}