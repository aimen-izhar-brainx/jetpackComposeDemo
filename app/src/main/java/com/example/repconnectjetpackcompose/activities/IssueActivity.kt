package com.example.repconnectjetpackcompose.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.example.repconnectjetpackcompose.R
import com.example.repconnectjetpackcompose.bottomNavItems.FeaturedCircularProgressIndicator
import com.example.repconnectjetpackcompose.bottomNavItems.TopBar
import com.example.repconnectjetpackcompose.models.MagazinesIssues
import com.example.repconnectjetpackcompose.models.Manufacturer
import com.example.repconnectjetpackcompose.viewModel.RepertoireViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IssueActivity : ComponentActivity() {
    var yearId: Int? = null
    private lateinit var repertoireViewModel: RepertoireViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentData()
        repertoireViewModel = ViewModelProvider(this)[RepertoireViewModel::class.java]

        setContent {
            var isLoading by remember { mutableStateOf(true) }
            val magList = remember { mutableStateOf(listOf<MagazinesIssues>()) }

            LaunchedEffect(Unit) {
                repertoireViewModel.getRepertoireMagazineYear(yearId.toString()){
                    if (it != null) {
                        magList.value = it
                        isLoading = false
                    }
                }
            }

            if (isLoading) {
                FeaturedCircularProgressIndicator(state = true)
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(id = R.color.white))
                ) {
                    magList.let { it1 ->
                        TopBar(title = getString(R.string.issue, yearId), drawable = R.drawable.ic_arrow_left_stroke)
                        IssueRcv(it1.value)
                    }
                }
            }
        }

    }

    private fun getIntentData() {
        yearId = intent.getIntExtra("year", -1)
    }
}


@Composable
fun IssueRcv(magazinesIssues: List<MagazinesIssues>) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxSize(),
        elevation = 0.dp,
        shape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp),
        backgroundColor = colorResource(id = R.color.color_pink_CB_op_40)
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(magazinesIssues, itemContent = { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(24.dp, 6.dp, 24.dp, 10.dp)

                ) {
                    AsyncImage(
                        model = item.ImageUrl,
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
                                text = item.Description.toString(),
                                color = colorResource(id = R.color.black),
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp,
                                fontFamily = FontFamily(
                                    Font(R.font.gilroy_bold),
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 12.dp, 0.dp, 12.dp)
                            )


                        }
                    }

                }
            })

        }
    }
}

