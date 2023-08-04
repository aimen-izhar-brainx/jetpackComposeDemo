package com.example.repconnectjetpackcompose.bottomNavItems

import android.content.Intent
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.repconnectjetpackcompose.R
import com.example.repconnectjetpackcompose.activities.BottomNavActivity
import com.example.repconnectjetpackcompose.activities.ManufacturerDetailScreen
import com.example.repconnectjetpackcompose.models.Manufacturer
import com.example.repconnectjetpackcompose.viewModel.ManufacturerViewModel

@Composable
fun ManufacturerRcv(manufacturerViewModel: ManufacturerViewModel) {
    val magList = remember { mutableStateOf(listOf<Manufacturer>()) }
    val loading = remember { mutableStateOf(magList.value.isEmpty()) }

    manufacturerViewModel.getManufacturerList {
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
            TopBar(
                title = stringResource(id = R.string.manufacturers),
                drawable = R.drawable.ic_icon_logo
            )
            ManufacturerRcvItem(magList.value)
        }
    }
}

@Composable
fun ManufacturerRcvItem(magList: List<Manufacturer>) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 56.dp)
    ) {
        items(items = magList, itemContent = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 0.dp, 24.dp, 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().clickable {
                        Intent(context, ManufacturerDetailScreen::class.java).apply {
                            putExtra("manufacturer",it)
                            context.startActivity(this)
                        }
                    },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        model = it.LogoUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
                            .width(51.dp)
                            .height(51.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Column(modifier = Modifier.weight(3f)) {
                        Text(
                            text = it.ContactName.toString(),
                            modifier = Modifier
                                .padding(4.dp, 0.dp, 6.dp, 0.dp),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.gilroy_semibold))
                        )
                        Text(
                            text = it.MainPhone.toString(),
                            modifier = Modifier.padding(4.dp, 0.dp, 6.dp, 0.dp),
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.gilroy_medium)),
                            color = colorResource(id = R.color.color_gray_98)
                        )

                    }
                   // Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.ic_call),
                        contentDescription = null,
                        modifier = Modifier.weight(1f).padding(0.dp, 0.dp, 2.dp, 0.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.chat),
                        contentDescription = null,
                        modifier = Modifier.weight(1f).padding(0.dp, 0.dp, 16.dp, 0.dp)


                    )

                }
            }
        })
    }
}