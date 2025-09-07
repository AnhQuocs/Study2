package com.example.test.upload_img

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileSetupScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UploadProfilePhotoBox(
            onImageSelected = { uri ->
                // Xử lý Uri ở đây
                // Ví dụ: lưu vào ViewModel
                if (uri != null) {
                    println("Ảnh đã chọn: $uri")
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { /* Submit form */ }) {
            Text("Tiếp tục")
        }
    }
}