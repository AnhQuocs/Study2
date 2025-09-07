package com.example.test.flights.presentation.share_test

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.test.flights.presentation.viewmodel.FlightViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ShareTestNavHost(
    navController: NavHostController = rememberNavController(),
    flightViewModel: FlightViewModel = hiltViewModel()
) {
    val flightList = flightViewModel.flights

    // SharedTransitionLayout ở ngoài NavHost → chung scope cho tất cả route
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = "main"
        ) {
            composable("main") {
                ShareTestMainScreen(
                    flightList = flightList,
                    onSelect = { flight ->
                        // Preload ảnh trước khi navigate
                        flightViewModel.preloadImage(flight.airlineLogoUrl)
                        navController.navigate("detail/${flight.id}")
                    },
                    transitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable
                )
            }

            composable(
                route = "detail/{flightId}",
                arguments = listOf(navArgument("flightId") { type = NavType.StringType })
            ) { backStackEntry ->
                val flightId = backStackEntry.arguments?.getString("flightId")
                val selectedFlight = flightList.find { it.id == flightId }

                selectedFlight?.let { flight ->
                    ShareTestDetailScreen(
                        onBack = { navController.popBackStack() },
                        selectedFlight = flight,
                        transitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@composable
                    )
                }
            }
        }
    }
}
