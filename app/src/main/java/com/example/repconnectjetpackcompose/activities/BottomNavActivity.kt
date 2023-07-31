package com.example.repconnectjetpackcompose.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.repconnectjetpackcompose.models.BottomNavItem
import com.example.repconnectjetpackcompose.models.HomeItem
import com.example.repconnectjetpackcompose.ui.theme.RepConnectJetpackComposeTheme
import com.example.repconnectjetpackcompose.viewModel.HomeViewModel
import com.example.repconnectjetpackcompose.viewModel.RepertoireViewModel
import com.example.repconnectjetpackcompose.R
import com.example.repconnectjetpackcompose.bottomNavItems.*
import com.example.repconnectjetpackcompose.viewModel.ManufacturerViewModel
import com.example.repconnectjetpackcompose.viewModel.PodcastViewModel

class BottomNavActivity : ComponentActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var repertoireViewModel: RepertoireViewModel
    private lateinit var manufacturerViewModel: ManufacturerViewModel
    private lateinit var podcastViewModel: PodcastViewModel
    private var feedList: ArrayList<HomeItem>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        repertoireViewModel = ViewModelProvider(this)[RepertoireViewModel::class.java]
        manufacturerViewModel = ViewModelProvider(this)[ManufacturerViewModel::class.java]
        podcastViewModel = ViewModelProvider(this)[PodcastViewModel::class.java]
        getHomeData()

    }

    private fun getHomeData() {
        setContent {
            var isLoading by remember { mutableStateOf(true) }
            var feedList by remember {  mutableStateOf(listOf<HomeItem>()) }

            LaunchedEffect(Unit) {
                homeViewModel.getHomeFeed("https://repertoiremag.com/category/repconnect-feed/feed") {
                    if (it != null) {
                        feedList = it
                        isLoading = false
                    }
                }
            }

            if (isLoading) {
                FeaturedCircularProgressIndicator(state = true)
            } else {
                FeaturedCircularProgressIndicator(state = false)
                RepConnectJetpackComposeTheme {
                    MainScreenView(feedList, repertoireViewModel, manufacturerViewModel,podcastViewModel)
                }
            }
        }
    }

}


@Composable
fun NavigationGraph(
    navController: NavHostController,
    homeViewModel: List<HomeItem>,
    repertoireViewModel: RepertoireViewModel,
    manufacturerViewModel: ManufacturerViewModel,
    podcastViewModel: PodcastViewModel,
) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            HomeScreen(homeViewModel)
        }
        composable(BottomNavItem.Podcast.screen_route) {
            PodcastRcv(podcastViewModel)
        }
        composable(BottomNavItem.Repertoire.screen_route) {
            RepertoireScreen(repertoireViewModel)
        }
        composable(BottomNavItem.Manufacturer.screen_route) {
            ManufacturerRcv(manufacturerViewModel)
        }
        composable(BottomNavItem.Video.screen_route) {
            VideoView()
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Repertoire,
        BottomNavItem.Manufacturer,
        BottomNavItem.Podcast,
        BottomNavItem.Video
    )
    androidx.compose.material.BottomNavigation(
        backgroundColor = colorResource(id = R.color.white),
        //contentColor = colorResource(id = R.color.color_gray_65)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy_medium)),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                selectedContentColor = colorResource(id = R.color.color_red_1A),
                unselectedContentColor = colorResource(id = R.color.color_gray_70),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun MainScreenView(
    homeViewModel: List<HomeItem>,
    repertoireViewModel: RepertoireViewModel,
    manufacturerViewModel: ManufacturerViewModel,
    podcastViewModel: PodcastViewModel
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {

        NavigationGraph(
            navController = navController,
            homeViewModel,
            repertoireViewModel,
            manufacturerViewModel,
            podcastViewModel
        )
    }
}