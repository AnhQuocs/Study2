package com.example.test.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.test.multiple_lang.presentation.HotelList
import com.example.test.multiple_lang.presentation.viewmodel.HotelViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HotelViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        LaunchedEffect(Unit) {
            viewModel.loadAfterLogin()
        }

        Column(
        ) {
            HotelList(viewModel = viewModel)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    Firebase.auth.signOut()
                    navController.navigate("dashboard") {
                        popUpTo("home") {inclusive = true}
                    }
                }
            ) {
                Text("Logout")
            }
        }
    }
}