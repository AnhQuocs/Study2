package com.example.test.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SearchScreen()
        }
    }
}

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    val suggestList = listOf(
        "Coral Coast Resort",
        "Khu nghỉ dưỡng Bờ Biển San Hô",
        "Sunleaf Villas",
        "Biệt thự Sunleaf",
        "Azure Roof Villas",
        "Biệt thự Mái Xanh Azure",
        "Verde Patio Hotel",
        "Khách sạn Sân Vườn Verde",
        "Casa Solana Resort",
        "Khu nghỉ dưỡng Casa Solana",
        "Luna Azul Resort",
        "Khu nghỉ dưỡng Luna Azul",
        "Palm Lotus Retreat",
        "Khu Nghỉ Dưỡng Palm Lotus",
        "Azure Palms Resort",
        "Khu nghỉ dưỡng Azure Palms",
        "Maui, Hawaii",
        "Hua Hin, Thailand",
        "Cancún, Mexico",
        "San José, Costa Rica",
        "Miami Beach, FL",
        "Kuta, Bali",
        "District 1, HCM City",
        "Seminyak, Bali",
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {  
        val textState = remember { mutableStateOf(TextFieldValue("")) }

        SearchView(state = textState, placeholder = "Search here...", modifier = modifier)

        val searchedText = textState.value.text

        if (searchedText.isNotEmpty()) {
            LazyColumn(modifier = Modifier.padding(10.dp)) {
                items(
                    items = suggestList.filter {
                        it.contains(searchedText, ignoreCase = true)
                    },
                    key = { it }
                ) { item ->
                    ColumnItem(item)
                }
            }
        }
    }
}

@Composable
fun ColumnItem(item: String) {
    Column(modifier = Modifier.padding(10.dp)) {
        Text(
            text = item,
            fontSize = 22.sp,
            modifier = Modifier
                .padding(vertical = 10.dp)
        )
        Divider()
    }
}

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    placeholder: String,
    state: MutableState<TextFieldValue>
) {
    TextField(
        value = state.value,
        onValueChange = { state.value = it },
        modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .clip(RoundedCornerShape(30.dp))
            .border(2.dp, color = Color.DarkGray, shape = RoundedCornerShape(30.dp)),
        placeholder = {
            Text(text = placeholder)
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 20.sp
        )
    )
}