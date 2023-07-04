package com.example.repconnectjetpackcompose.bottomNavItems

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
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
import com.example.repconnectjetpackcompose.activities.IssueActivity
import com.example.repconnectjetpackcompose.models.MagazineYear
import com.example.repconnectjetpackcompose.viewModel.RepertoireViewModel


@Composable
fun RepertoireScreen(repertoireViewModel: RepertoireViewModel) {
    val magList = remember { mutableStateOf(listOf<MagazineYear>()) }
    val loading = remember { mutableStateOf(magList.value.isEmpty()) }

    repertoireViewModel.getRepertoireMagazines {
        if (it != null) {
            magList.value = it
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
            TopBar(title = stringResource(id = R.string.repertoire), drawable = R.drawable.ic_icon_logo)
            RepertoireRcv(magList.value)
        }
    }
}

@Composable
fun TopBar(title: String, drawable: Int) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Image(
            painter = painterResource(id = drawable),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically),
            )
        Spacer(modifier = Modifier.weight(1.0f, true))
        Text(
            text = title,
            color = colorResource(id = R.color.black),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.gilroy_semibold))
        )

        Spacer(Modifier.weight(1.0f, fill = true))
        Image(
            painter = painterResource(id = R.drawable.ic_icon_search),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically),

            )

    }
}

@Composable
fun RepertoireRcv(magList: List<MagazineYear>) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .padding(24.dp, 0.dp, 24.dp, 56.dp)
    ) {
        items(items = magList, itemContent = {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorResource(id = R.color.white))
                    .padding(0.dp, 16.dp, 0.dp, 0.dp)
                    .clickable {
                        Intent(context, IssueActivity::class.java).apply {
                            putExtra("year", it.YearId)
                            context.startActivity(this)
                        }
                    },
                model = it.ImageUrl,
                //placeholder = painterResource(id = R.drawable.placeholder),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )
        })
    }
}

@Composable
fun FeaturedCircularProgressIndicator(state: Boolean) {
    if (state) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.padding(8.dp),
                color = colorResource(id = R.color.color_red_1A),

            )
        }

    }
}

