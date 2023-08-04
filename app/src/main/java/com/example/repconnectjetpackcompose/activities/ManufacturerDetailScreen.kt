package com.example.repconnectjetpackcompose.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.repconnectjetpackcompose.R
import com.example.repconnectjetpackcompose.models.Manufacturer

class ManufacturerDetailScreen : ComponentActivity() {
    private var manufacturer: Manufacturer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentData()
        setContent {
            companyInfo()
        }
    }

    private fun getIntentData() {
        manufacturer = intent.getSerializableExtra("manufacturer") as? Manufacturer?
    }

    @Composable
    fun companyInfo() {
        Column(modifier = Modifier.fillMaxSize()) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(
                        colorResource(id = R.color.white),
                        RoundedCornerShape(8.dp)
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = 16.dp, 24.dp, 16.dp, 16.dp)
                ) {
                    AsyncImage(
                        model = manufacturer?.LogoUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .width(66.dp)
                            .height(66.dp)
                            .padding(top = 26.dp)
                    )
                    manufacturer?.ContactName?.let {
                        Text(
                            text = it, fontSize = 20.sp, fontFamily = FontFamily(
                                Font(R.font.gilroy_semibold)
                            ), modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                    manufacturer?.Address?.let {
                        Text(
                            text = it,
                            fontSize = 11.sp,
                            color = colorResource(
                                id = R.color.color_black_op_50
                            ),
                            fontFamily = FontFamily(Font(R.font.gilroy_semibold)),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    Row(modifier = Modifier.padding(top = 14.dp, bottom = 26.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_call_bg_circle),
                            contentDescription = null,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_chat_bg_circle),
                            contentDescription = null,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_wec_bg_circle),
                            contentDescription = null,
                            modifier = Modifier.padding(end = 16.dp)
                        )

                    }


                }

            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(
                        colorResource(id = R.color.white),
                        RoundedCornerShape(8.dp)
                    )
            ) {
                Column() {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp,8.dp,16.dp,8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_call_icon),
                            contentDescription = null
                        )
                        Text(text = stringResource(id = R.string.phone))
                        Spacer(modifier = Modifier.weight(1f))
                        manufacturer?.MainPhone?.let { Text(text = it) }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp,0.dp,16.dp,8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_call_icon),
                            contentDescription = null
                        )
                        Text(text = stringResource(id = R.string.fax))
                        Spacer(modifier = Modifier.weight(1f))
                        manufacturer?.MainFax?.let { Text(text = it) }
                    }
                }
            }
        }
    }
}