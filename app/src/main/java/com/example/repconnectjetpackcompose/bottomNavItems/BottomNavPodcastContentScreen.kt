package com.example.repconnectjetpackcompose.bottomNavItems

import Podcast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.repconnectjetpackcompose.R
import com.example.repconnectjetpackcompose.models.Manufacturer
import com.example.repconnectjetpackcompose.utils.shareData
import com.example.repconnectjetpackcompose.viewModel.ManufacturerViewModel
import com.example.repconnectjetpackcompose.viewModel.PodcastViewModel

@Composable
fun PodcastRcv(podcastViewModel: PodcastViewModel) {
    val podcastList = remember { mutableStateOf(listOf<Podcast>()) }
    val loading = remember { mutableStateOf(podcastList.value.isEmpty()) }

    podcastViewModel.getPodcastList("https://feeds.libsyn.com/413912/rss") {
        if (it != null) {
            podcastList.value = it
            loading.value = false
        } else {
            loading.value = false
        }
    }
    if (loading.value)
        FeaturedCircularProgressIndicator(state = loading.value)
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
        ) {
            TopBar(
                title = stringResource(id = R.string.podcasts),
                drawable = R.drawable.ic_icon_logo
            )
            PodcastRcvItem(podcastList.value)
        }
    }
}

@Composable
fun PodcastRcvItem(magList: List<Podcast>) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 56.dp)
    ) {
        items(items = magList, itemContent = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        model = it.image,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(10.dp)
                            .width(100.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Column(modifier = Modifier.weight(3f)) {
                        Text(
                            text = it.title.toString(),
                            modifier = Modifier
                                .padding(4.dp, 0.dp, 6.dp, 0.dp),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.gilroy_semibold))
                        )
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)) {
                            Box(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .height(28.dp)
                                    .background(
                                        color = colorResource(id = R.color.color_red_1A),
                                        shape = RoundedCornerShape(100.dp)
                                    )
                            ) {
                                Row(Modifier.width(68.dp).align(Alignment.Center), horizontalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_play_icon),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(0.dp, 2.dp, 4.dp, 0.dp)
                                    )
                                    Text(
                                        text = "Play",
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Center,
                                        color = colorResource(id = R.color.white),
                                        fontFamily = FontFamily(Font(R.font.gilroy_semibold))
                                    )
                                }
                            }
                            Image(
                                painter = painterResource(id = R.drawable.ic_download_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(10.dp, 0.dp, 2.dp, 0.dp)
                                    .clickable {
                                        it.link?.let { it1 -> shareData(it1, context) }
                                    }
                            )
                        }
                    }
                   // Spacer(modifier = Modifier.weight(1f))

                }
        })
    }
}