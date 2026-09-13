package com.deep.compose_project.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deep.compose_project.R


@Preview
@Composable
fun LoginScreen(navController : NavController?=null){
    var number by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.orangeColor))
    ) {


        Image(painterResource(R.drawable.ic_logo), contentDescription = "", Modifier.fillMaxWidth().height(300.dp).padding(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(
                    RoundedCornerShape(
                        topStart = 30.dp,
                        topEnd = 30.dp
                    )
                )
                .background(colorResource(R.color.blueColor)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Login ID",  style = TextStyle(fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold), modifier = Modifier.padding(top = 30.dp))
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = number,
                onValueChange = {
                    if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                        number = it
                    }
                },
                label = {
                    Text("Enter Your Login ID")
                },
                placeholder = {
                    Text("Enter Your Login ID")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedTextColor = Color.White,
                    errorTextColor = Color.White,
                    cursorColor = Color.White,


                    // Hint / Placeholder
                    focusedPlaceholderColor = Color.White,
                    unfocusedPlaceholderColor = Color.White,

                    // Label
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),

                )
            TextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text("Enter Your Password")
                },
                placeholder = {
                    Text("Enter Your Password")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedTextColor = Color.White,
                    errorTextColor = Color.White,
                    cursorColor = Color.White,

                    // Hint / Placeholder
                    focusedPlaceholderColor = Color.White,
                    unfocusedPlaceholderColor = Color.White,

                    // Label
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                ),

                )
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp) // horizontal margin
                    .clip(RoundedCornerShape(30.dp))
                    .background(colorResource(R.color.orangeColor))
                    .clickable{
                        navController!!.navigate("home")
                    }
            ) {
                Text(
                    text = "Login",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = stringResource(R.string.textphoneauth),
                style = TextStyle(fontSize = 16.sp, color = Color.White,
                    textAlign = TextAlign.Center), modifier = Modifier.padding(top = 30.dp))
        }

    }
}