package com.example.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test.auth.HomeScreen
import com.example.test.dashboard.DashboardScreen
import com.example.test.multiple_lang.data.repository.HotelRepository
import com.example.test.multiple_lang.domain.usecase.GetHotelsUseCase
import com.example.test.multiple_lang.for_values.MyApp
import com.example.test.multiple_lang.presentation.viewmodel.HotelViewModel
import com.example.test.ui.theme.TestTheme
import com.example.test.weather.presentation.ui.WeatherSection
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            val context = LocalContext.current
            val viewModel = remember { HotelViewModel(GetHotelsUseCase(HotelRepository()), context) }

            TestTheme {
//                AuthApp(viewModel)
//                WeatherSection(context = context)
                MyApp()
            }
        }
    }
}

@Composable
fun AuthApp(viewModel: HotelViewModel) {
    val navController = rememberNavController()

    val startDestination by remember {
        mutableStateOf(
            if(FirebaseAuth.getInstance().currentUser != null) "home"
            else "dashboard"
        )
    }

    NavHost(navController, startDestination = startDestination) {
        composable("dashboard") { DashboardScreen(navController) }
        composable("home") { HomeScreen(navController, viewModel) }
    }
}

@Composable
fun MainScreen() {

}

// MainScreen Old:
//val context = LocalContext.current
//val scope = rememberCoroutineScope()
//
//val lazyListState = rememberLazyListState()
//
//val reorderItem = remember {
//    mutableStateListOf<String>().apply {
//        addAll(List(50) { "Item $it" })
//    }
//}
//
//LaunchedEffect(Unit) {
//    val saved = getSavedListOrder(context)
//    if (saved != null) {
//        // Lưu vị trí hiện tại
//        val currentIndex = lazyListState.firstVisibleItemIndex
//        val currentOffset = lazyListState.firstVisibleItemScrollOffset
//
//        reorderItem.clear()
//        reorderItem.addAll(saved)
//
//        // Scroll lại về vị trí cũ
//        scope.launch {
//            lazyListState.scrollToItem(currentIndex, currentOffset)
//        }
//    }
//}
//
//Column(
//modifier = Modifier.fillMaxSize()
//) {
//    DragDropList(
//        items = reorderItem,
//        onMove = { fromIndex, toIndex ->
//            reorderItem.move(fromIndex, toIndex)
//            scope.launch {
//                saveListOrder(context, reorderItem)
//            }
//        },
//        modifier = Modifier.fillMaxSize(),
//        lazyListState = lazyListState
//    )
//}

//private fun createNotificationChannel(context: Context) {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val channel = NotificationChannel(
//            "reminder_channel",
//            "Lời nhắc",
//            NotificationManager.IMPORTANCE_HIGH
//        ).apply {
//            description = "Kênh cho lời nhắc tự động"
//        }
//
//        val manager = context.getSystemService(NotificationManager::class.java)
//        manager.createNotificationChannel(channel)
//    }
//}
//
//private fun setAlarm(context: Context) {
//    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//    val intent = Intent(context, AlarmReceiver::class.java)
//    val pendingIntent = PendingIntent.getBroadcast(
//        context,
//        0,
//        intent,
//        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//    )
//
//    val calendar = Calendar.getInstance().apply {
//        timeInMillis = System.currentTimeMillis()
//        set(Calendar.HOUR_OF_DAY, 20)
//        set(Calendar.MINUTE, 42)
//        set(Calendar.SECOND, 0)
//    }
//
//    alarmManager.setExactAndAllowWhileIdle(
//        AlarmManager.RTC_WAKEUP,
//        calendar.timeInMillis,
//        pendingIntent
//    )
//}

//                MainScreen()
//                val postNotificationPermission =
//                    rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
//
//                val waterNotificationService = WaterNotificationService(context = this)
//
//                LaunchedEffect(key1 = true) {
//                    if (!postNotificationPermission.status.isGranted) {
//                        postNotificationPermission.launchPermissionRequest()
//                    }
//                }
//
//                createNotificationChannel(this)
//                setAlarm(this)

//                Column {
//                    Button(
//                        onClick = {
//                            waterNotificationService.showBasicNotification()
//                        }
//                    ) {
//                        Text("Show basic notification")
//                    }
//
//                    Button(
//                        onClick = {
//                            waterNotificationService.showExpandableNotification()
//                        }
//                    ) {
//                        Text("Show expandable with image")
//                    }
//
//                    Button(
//                        onClick = {
//                            waterNotificationService.showExpandableLongText()
//                        }
//                    ) {
//                        Text("Show expandable with text notification")
//                    }
//
//                    Button(
//                        onClick = {
//                            waterNotificationService.showInboxStyleNotification()
//                        }
//                    ) {
//                        Text("Show inbox-style notification")
//                    }
//
//                    Button(
//                        onClick = {
//                            waterNotificationService.showNotificationGroup()
//                        }
//                    ) {
//                        Text("Show inbox-style notification")
//                    }
//                }