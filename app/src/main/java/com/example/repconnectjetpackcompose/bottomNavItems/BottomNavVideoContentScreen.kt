package com.example.repconnectjetpackcompose.bottomNavItems

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.repconnectjetpackcompose.R
import com.example.repconnectjetpackcompose.activities.IssueActivity
import com.example.repconnectjetpackcompose.activities.VideoPlayerAcitvity
import com.example.repconnectjetpackcompose.models.Manufacturer
import com.example.repconnectjetpackcompose.models.MdsVideo
import com.example.repconnectjetpackcompose.viewModel.ManufacturerViewModel
import com.example.repconnectjetpackcompose.viewModel.VideoViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun VideoView(videoViewModel: VideoViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        TopBar(
            title = stringResource(id = R.string.videos),
            drawable = R.drawable.ic_icon_search
        )
        CombinedTab(videoViewModel)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CombinedTab(videoViewModel: VideoViewModel) {
    val tabData = listOf<String>(
        "Customer", "end user",
    )
    val pagerState = rememberPagerState(
        pageCount = tabData.size
    )
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    Column {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .pagerTabIndicatorOffset(pagerState, tabPositions)
                        .width(0.dp)
                        .height(0.dp)
                )
            }, backgroundColor = colorResource(id = R.color.color_gray_F6_op_40),
            modifier = Modifier
                .padding(24.dp, 0.dp, 24.dp, 24.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            tabData.forEachIndexed { index, pair ->
                Tab(selected = tabIndex == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = pair,
                            color = colorResource(id = R.color.black),
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.gilroy_bold))
                        )
                    })
            }
        }
        HorizontalPager(
            state = pagerState,
            //modifier = Modifier.weight(1f)
        ) { index ->
            tabContent(tabData, index, videoViewModel)
        }
    }
}

@Composable
fun tabContent(tabData: List<String>, index: Int, videoViewModel: VideoViewModel) {
    val mdList = remember { mutableStateOf(listOf<MdsVideo>()) }
    val loading = remember { mutableStateOf(mdList.value.isEmpty()) }
    if(index == 0) {
        videoViewModel.getVideos {
            if (it != null) {
                mdList.value = it
                loading.value = false
            } else {
                loading.value = false
            }
        }
    } else {
        videoViewModel.getCustomerVideos {
            if (it != null) {
                mdList.value = it
                loading.value = false
            } else {
                loading.value = false
            }
        }
    }
    if (loading.value)
            FeaturedCircularProgressIndicator(state = loading.value)
        else {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
            ) {
                VideoRcvItems(videoList = mdList.value)

            }
        }
}

@Composable
fun VideoRcvItems(videoList: List<MdsVideo>) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 56.dp)
    ) {
        items(items = videoList, itemContent = {
            Card(
                modifier = Modifier
                    .background(colorResource(id = R.color.white), RoundedCornerShape(6.dp))
                    .padding(16.dp, 8.dp, 16.dp, 8.dp), elevation = 4.dp
            ) {
                Row(
                    Modifier
                        .clickable {
                            Intent(context, VideoPlayerAcitvity::class.java).apply {
                                putExtra("url", it.VideoUrl)
                                context.startActivity(this)
                            }
                        }
                        .fillMaxWidth()
                        .height(67.dp), verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier.wrapContentWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = it.VideoThumbUrl,
                            contentDescription = null,
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_video_play_icon),
                            contentDescription = null,
                        )
                    }
                    it.Description?.let { it1 -> Text(text = it1, modifier = Modifier.padding(12.dp), fontFamily = FontFamily(Font(R.font.gilroy_bold)), fontSize = 12.sp) }

                }
            }
        })
    }
}