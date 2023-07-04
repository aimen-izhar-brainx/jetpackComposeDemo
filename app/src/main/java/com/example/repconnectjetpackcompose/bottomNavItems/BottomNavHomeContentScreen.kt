package com.example.repconnectjetpackcompose.bottomNavItems

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.repconnectjetpackcompose.R
import com.example.repconnectjetpackcompose.activities.WebViewActivity
import com.example.repconnectjetpackcompose.models.HomeItem
import com.example.repconnectjetpackcompose.utils.Utils.formatString
import com.example.repconnectjetpackcompose.utils.Utils.getNotificationAgoTime

@Composable
fun HomeScreen(feedList: List<HomeItem>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        HomeTopBar()
        feedList.let { HomeFeedNewsRcv(it) }
    }
}

@Composable
fun HomeTopBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_icon_logo),
            tint = colorResource(id = R.color.color_red_1A),
            contentDescription = null
        )
        Spacer(modifier = Modifier.weight(1f, true))
        Text(
            text = stringResource(id = R.string.featured_articles),
            color = colorResource(id = R.color.black),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.gilroy_semibold))
        )

        Spacer(Modifier.weight(1f, fill = true))
        Image(
            painter = painterResource(id = R.drawable.ic_icon_search),
            contentDescription = null,
            modifier = Modifier.padding(0.dp, 0.dp, 16.dp, 0.dp)

        )
        Image(
            painter = painterResource(id = R.drawable.ic_icon_setting),
            contentDescription = null
        )

    }
}

@Composable
fun HomeFeedNewsRcv(feedList: List<HomeItem>) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxSize(),
        elevation = 0.dp,
        shape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp),
        backgroundColor = colorResource(id = R.color.color_gray_F6_op_40)
    ) {
        LazyColumn(
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 56.dp)
        ) {
            items(items = feedList, itemContent = {
                HomeFeedItems(context, it)

            })
        }
    }
}

@Composable
private fun HomeFeedItems(
    context: Context,
    it: HomeItem
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(24.dp, 6.dp, 24.dp, 10.dp)
            .clickable {
                Intent(context, WebViewActivity::class.java).apply {
                    putExtra("url", it.link)
                    context.startActivity(this)
                }
            }
    ) {
        AsyncImage(
            model = it.image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.white))
                .clip(RoundedCornerShape(6.dp, 6.dp, 0.dp, 0.dp)),
            contentScale = ContentScale.FillWidth,
        )
        Card(
            shape = RoundedCornerShape(4.dp, 4.dp, 4.dp, 4.dp),
            backgroundColor = colorResource(id = R.color.white),
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 0.dp,

            ) {
            Column() {
                Text(
                    text = it.title.toString(),
                    color = colorResource(id = R.color.black),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(
                        Font(R.font.gilroy_bold),
                    ),
                    modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 6.dp)
                )
                Text(
                    text = getNotificationAgoTime(context, it.pubDate?.formatString()),
                    color = colorResource(id = R.color.color_black_op_50),
                    fontSize = 11.sp,
                    fontFamily = FontFamily(
                        Font(R.font.gilroy_semibold),
                    ),
                    modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 11.dp)
                )

            }
        }

    }
}


@Composable
fun PodcastScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.teal_700))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Podcast",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}