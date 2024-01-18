package com.example.littlelemon.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.R
import com.example.littlelemon.model.AppViewModel
import com.example.littlelemon.model.UserCredentials
import com.example.littlelemon.navigation.OnboardingScreen
import com.example.littlelemon.ui.theme.Black
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.theme.Yellow
import com.example.littlelemon.ui.theme.YellowBorder
import com.example.littlelemon.ui.theme.karlaFont

@Composable
fun Profile(navController: NavHostController, viewModel: AppViewModel) {

    val userCredentialsData: UserCredentials = viewModel.getUserData()

    LittleLemonTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                ProfileHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                )
                ProfileDetails(
                    modifier = Modifier
                    .fillMaxWidth(),
                    userCredentials = userCredentialsData,
                    onLogOutClick = {
                        viewModel.clearUserData()
//                        navController.navigate(route = OnboardingScreen.route)
                        navController.popBackStack(route = OnboardingScreen.route, inclusive = false)
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Image(
            modifier = modifier
                .height(70.dp)
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.app_logo)
        )
        Column(
            modifier = modifier
                .padding(vertical = 40.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.heading),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun ProfileDetails(userCredentials: UserCredentials, onLogOutClick: () -> Unit, modifier: Modifier = Modifier,) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp,vertical = 10.dp)
    ) {
        UserDataLayout(label = stringResource(id = R.string.first_name), userCredentials.firstName, modifier)
        UserDataLayout(label = stringResource(id = R.string.last_name), userCredentials.lastName, modifier)
        UserDataLayout(label = stringResource(id = R.string.email), userCredentials.email, modifier)
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
            onClick = onLogOutClick
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                fontFamily = karlaFont,
                text = stringResource(id = R.string.logout)
            )
        }
    }
}

@Composable
fun UserDataLayout(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(top = 20.dp)
    ) {
        Text(text = label)
        OutlinedCard(
            border = BorderStroke(1.dp, Color.Gray),
            modifier = modifier
                .padding(top = 5.dp)
        ) {
            Text(
                text =  value,
                modifier = Modifier
                    .padding(20.dp),
                maxLines = 1,
            )
        }
    }
}

