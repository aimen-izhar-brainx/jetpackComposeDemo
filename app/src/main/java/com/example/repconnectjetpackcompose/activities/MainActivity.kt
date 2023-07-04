package com.example.repconnectjetpackcompose.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.repconnectjetpackcompose.R
import com.example.repconnectjetpackcompose.ui.theme.RepConnectJetpackComposeTheme

class MainActivity : ComponentActivity() {
    private var userList = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userList = arrayListOf(
            getString(R.string.manufacturer_rep),
            getString(R.string.distributor_rep),
            getString(R.string.other)
        )
        setContent {
            RepConnectJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    RepConnectTypes(userList)
                }
            }
        }
    }
}

@Composable
fun RepConnectTypes(userList: ArrayList<String>) {
    val context = LocalContext.current
    Column() {
        Spacer(modifier = Modifier.weight(1.0f))
        Text(
            text = stringResource(id = R.string.type_of_rep_connect_user_u_are),
            fontFamily = FontFamily(
                Font(R.font.gilroy_semibold)
            ),
            fontSize = 30.sp,
            color = colorResource(id = R.color.color_black_32),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
        UserTypeRcv(userList)
        Spacer(modifier = Modifier.weight(3.0f))
        Button(
            onClick = {
                Intent(context, DistributorActivity::class.java).apply {
                    context.startActivity(this)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(24.dp, 0.dp, 24.dp, 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.color_red_1A))

        ) {
            Text(
                text = stringResource(id = R.string.next),
                color = colorResource(id = R.color.white),
                fontSize = 16.sp,
                fontFamily = FontFamily(
                    Font(R.font.gilroy_semibold)
                )
            )
        }


    }

}

@Composable
fun UserTypeRcv(userList: ArrayList<String>) {
    val selected = remember { mutableStateOf(userList[0]) }
    userList.forEach {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp, 0.dp, 16.dp, 8.dp),
            elevation = 0.dp,
            shape = RoundedCornerShape(16.dp),
            backgroundColor = colorResource(id = R.color.color_gray_F6)
        ) {
            Row(
                Modifier.selectable(
                    selected = (selected.value == it),
                    onClick = { selected.value = it },
                    role = Role.RadioButton
                )
            ) {
                Image(
                    painter = if (selected.value == it) painterResource(id = R.drawable.checked_radio_button) else painterResource(
                        id = R.drawable.uncheck_radio_button
                    ),
                    contentDescription = null,
                    modifier = Modifier.padding(21.dp, 21.dp, 10.dp, 21.dp)
                )
                Text(
                    text = it,
                    fontFamily = FontFamily(
                        Font(R.font.gilroy_medium)
                    ),
                    fontSize = 15.sp,
                    color = colorResource(id = R.color.color_black_32),
                    modifier = Modifier.padding(21.dp, 21.dp, 0.dp, 21.dp)
                )

            }
        }

    }
}
