package com.example.littlelemon.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.R
import com.example.littlelemon.model.AppViewModel
import com.example.littlelemon.model.UserCredentials
import com.example.littlelemon.navigation.HomeScreen
import com.example.littlelemon.ui.theme.Black
import com.example.littlelemon.ui.theme.Green
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.theme.White
import com.example.littlelemon.ui.theme.Yellow
import com.example.littlelemon.ui.theme.YellowBorder
import com.example.littlelemon.ui.theme.karlaFont

@Composable
fun Onboarding(
    navController: NavHostController,
    viewModel: AppViewModel
) {
    LittleLemonTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Header(
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Details(
                    modifier = Modifier,
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun Header(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Image(
            modifier = modifier
                .height(70.dp)
                .padding(vertical = 10.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.app_logo)
        )
        Column(
            modifier = modifier
                .background(Green)
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.greeting),
                color = White,
                fontWeight = FontWeight.Bold,
            )
        }
    }

}

@Composable
fun Details(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel,
    navController: NavHostController
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var validInfo by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Surface(
            modifier = Modifier
                .padding(vertical = 20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.heading),
                fontFamily = karlaFont,
                fontWeight = FontWeight.Bold
            )
        }
        if(!validInfo) {
            Text(
                text = "Registration unsuccessful. Please enter all data."
            )
        }
        InputText(
            lable = stringResource(id = R.string.first_name),
            value = firstName,
            onValueChange = { firstName = it }
        )
        InputText(
            lable = stringResource(id = R.string.last_name),
            value = lastName,
            onValueChange = { lastName = it}
        )
        InputText(
            lable = stringResource(id = R.string.email),
            value = email,
            onValueChange = { email = it }
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Yellow,
                contentColor = Black
            ),
            border = BorderStroke(2.dp, YellowBorder),
            shape = RoundedCornerShape(5.dp),
            onClick = {
                Log.d("Onboarding","$firstName $lastName $email")
                validInfo = if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty()) {
                    viewModel.addUserCredentials(UserCredentials(firstName, lastName, email))
                    navController.navigate(HomeScreen.route)
                    true
                } else {
                    false
                }
            }
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                fontFamily = karlaFont,
                text = stringResource(id = R.string.register)
            )
        }
    }
}

@Composable
fun InputText(lable: String, value: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
    ) {
        Text(text = lable)
        OutlinedTextField(
            placeholder = { Text(lable) },
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            singleLine = true,
        )
    }
}

