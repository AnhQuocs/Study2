package com.example.test

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.test.flights.domain.model.Flight
import com.example.test.flights.presentation.segmented_tab.SegmentedSection
import com.example.test.flights.presentation.share_test.ShareTestDetailScreen
import com.example.test.flights.presentation.share_test.ShareTestMainScreen
import com.example.test.flights.presentation.share_test.ShareTestNavHost
import com.example.test.flights.presentation.ui.DetailScreen
import com.example.test.flights.presentation.ui.ListScreen
import com.example.test.flights.presentation.viewmodel.FlightViewModel
import com.example.test.indicator_tab.TabScreen
import com.example.test.language.data.prefecences.LanguagePreferenceManager
import com.example.test.language.domain.model.AppLanguage
import com.example.test.language.utils.LanguageManager
import com.example.test.ui.theme.TestTheme
import com.example.test.upload_img.ProfileSetupScreen
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalSharedTransitionApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val updatedContext = runBlocking {
            val manager = LanguagePreferenceManager(newBase)
            val lang = manager.languageFlow.firstOrNull() ?: AppLanguage.ENGLISH
            LanguageManager.setAppLocale(newBase, lang)
        }
        super.attachBaseContext(updatedContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            TestTheme {
//                val flightViewModel: FlightViewModel = hiltViewModel()
//
//                val flightList = flightViewModel.flights
//
//                var selectedFlight by remember {
//                    mutableStateOf<Flight?>(null)
//                }
//
//                var showDetails by remember {
//                    mutableStateOf(false)
//                }
//
//                SharedTransitionLayout {
//                    AnimatedContent(targetState = showDetails, label = "transition") { isVisible ->
//                        if(!isVisible) {
//                            ShareTestMainScreen(
//                                flightList = flightList,
//                                onSelect = { flight ->
//                                    selectedFlight = flight
//                                    showDetails = true
//                                },
//                                transitionScope = this@SharedTransitionLayout,
//                                animatedVisibilityScope = this@AnimatedContent
//                            )
//                        } else {
//                            selectedFlight?.let { flight ->
//                                ShareTestDetailScreen(
//                                    onBack = { showDetails = false },
//                                    selectedFlight = flight,
//                                    transitionScope = this@SharedTransitionLayout,
//                                    animatedVisibilityScope = this@AnimatedContent
//                                )
//                            }
//                        }
//                    }
//                }
//
//                ShareTestNavHost()

                ProfileSetupScreen()

//                SegmentedSection()
            }
        }
    }
}

//TestTheme {
//                FlightList()
//                TabScreen()
//}

//TestTheme {
//    val context = LocalContext.current
//                val navController = rememberNavController()
//
//                SharedTransitionLayout {
//                    NavHost(
//                        navController = navController,
//                        startDestination = "list"
//                    ) {
//                        composable("list") {
//                            ListScreen(
//                                onItemClick = { imgUrl, flight ->
//                                    val encodedUrl =
//                                        URLEncoder.encode(imgUrl, StandardCharsets.UTF_8.toString())
//                                    navController.currentBackStackEntry?.savedStateHandle?.set(
//                                        "flight",
//                                        flight
//                                    )
//                                    navController.navigate("detail/$encodedUrl")
//                                },
//                                animatedVisibilityScope = this
//                            )
//                        }
//
//                        composable(
//                            "detail/{imgUrl}",
//                            arguments = listOf(
//                                navArgument("imgUrl") {
//                                    type = NavType.StringType
//                                }
//                            )
//                        ) { backStackEntry ->
//                            val encodedUrl = backStackEntry.arguments?.getString("imgUrl") ?: ""
//                            val imgUrl =
//                                URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.toString())
//
//                            val flight = navController.previousBackStackEntry
//                                ?.savedStateHandle
//                                ?.get<Flight>("flight")
//
//                            flight?.let {
//                                DetailScreen(
//                                    imgUrl = imgUrl,
//                                    flight = it,
//                                    animatedVisibilityScope = this
//                                )
//                            }
//                        }
//                    }
//                }
//}

//@Composable
//fun AuthApp(viewModel: HotelViewModel) {
//    val navController = rememberNavController()
//
//    val startDestination by remember {
//        mutableStateOf(
//            if(FirebaseAuth.getInstance().currentUser != null) "home"
//            else "dashboard"
//        )
//    }
//
//    NavHost(navController, startDestination = startDestination) {
//        composable("dashboard") { DashboardScreen(navController) }
//        composable("home") { HomeScreen(navController, viewModel) }
//    }
//}

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