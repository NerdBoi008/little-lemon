package com.example.littlelemon.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.R
import com.example.littlelemon.navigation.ProfileScreen

@Composable
fun Home(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        HomeHeader(
            modifier = Modifier,
            onProfileCLick = {
                navController.navigate(ProfileScreen.route)
            }
        )
    }
}

@Composable
fun HomeHeader(modifier: Modifier = Modifier, onProfileCLick: () -> Unit) {
    Row(
        modifier = modifier
                .height(70.dp)
                .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = modifier
                .fillMaxHeight()
                .padding(vertical = 10.dp)
                .weight(1f),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.app_logo)
        )
        Image(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .clickable { onProfileCLick() },
            contentScale = ContentScale.Fit,
            painter = painterResource(id = R.drawable.profile),
            contentDescription = stringResource(id = R.string.profile_image)
        )
    }

}

@Preview(showSystemUi = true, apiLevel = 33)
@Composable
fun ProfileScreenPreview() {
    HomeHeader(onProfileCLick = {})
}

