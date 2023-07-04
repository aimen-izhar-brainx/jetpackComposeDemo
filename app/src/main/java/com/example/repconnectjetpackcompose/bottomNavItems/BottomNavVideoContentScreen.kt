package com.example.repconnectjetpackcompose.bottomNavItems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.repconnectjetpackcompose.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun VideoView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        TopBar(
            title = stringResource(id = R.string.videos),
            drawable = R.drawable.ic_icon_search
        )
        CombinedTab()
        /* Column() {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                tabItems.forEachIndexed { index, s ->
                    Tab(selected = pagerState.currentPage == index, onClick = { coroutineScope.launch {  pagerState.animateScrollToPage(index)} }) {
                      Text(text = s)
                    }
                }
            }
            HorizontalPager(state = pagerState) {
                Text(text = tabItems[it])
            }
        }
    }*/

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CombinedTab() {
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
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions).width(0.dp).height(0.dp)
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
                            fontFamily = FontFamily(Font(R.font.gilroy_bold)))
                    })
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = tabData[index]
                )
            }
        }
    }
}