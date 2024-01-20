package com.example.littlelemon.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.R
import com.example.littlelemon.model.AppViewModel
import com.example.littlelemon.navigation.ProfileScreen
import com.example.littlelemon.ui.theme.Black
import com.example.littlelemon.ui.theme.Green
import com.example.littlelemon.ui.theme.White
import com.example.littlelemon.ui.theme.Yellow


@Composable
fun Home(navController: NavHostController, viewModel: AppViewModel) {

    val networkMenu by viewModel.menuItemsState.collectAsState()
    Log.d("HOME", "$networkMenu")

    var searchString by remember { mutableStateOf("") }

    LaunchedEffect(networkMenu) {
        viewModel.getFoodMenuLocal()
    }

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
        LazyColumn() {
            item {
                Hero(
                    onSearch = {
                        searchString = it
                        Log.d("HOME", searchString)
                    }
                )
                CategorySection()
            }
            if (searchString.isNotEmpty()) {
                items(networkMenu.filter {
                    it.title.contains(searchString)
                }) {
                        Log.e("HOME", "inside if $it")
                    MenuItemLayout(
                        title = it.title,
                        description = it.description,
                        price = it.price,
                        imageSource = it.image
                    )
                }
            } else {
                items(networkMenu) {
                    Log.i("HOME", "inside else $it")
                    MenuItemLayout(
                        title = it.title,
                        description = it.description,
                        price = it.price,
                        imageSource = it.image
                    )
                }
            }
        }
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

@Composable
fun Hero(onSearch: (String) -> Unit ) {

    var searchPhrase by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(Green)
            .padding(15.dp),
    ) {
        Text(
            text = stringResource(id = R.string.hero_heading),
            modifier = Modifier,
            color = Yellow,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 35.sp,
        )
        Row() {
            Column(
                modifier = Modifier
                    .weight(1f),
            ) {
                Text(
                    text = stringResource(id = R.string.hero_city),
                    modifier = Modifier,
                    color = White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 25.sp,
                )
                Text(
                    modifier = Modifier
                        .padding(top = 20.dp),
                    text = stringResource(id = R.string.hero_desc),
                    color = White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 17.sp,
                )
            }
            Image(
                modifier = Modifier
                    .size(125.dp, 150.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = stringResource(id = R.string.hero_heading),
            )
        }
        OutlinedCard(
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 10.dp)
//                .height(40.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFD6D6D6)
            )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier,
            ) {
//                Image(painter = , contentDescription = )
                TextField(
                    modifier = Modifier
                        .padding(start = 10.dp),
                    value = searchPhrase,
                    onValueChange = {
                        searchPhrase = it
                        onSearch(it)
                    },
                    placeholder = { Text(text = stringResource(id = R.string.search_query))},
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFD6D6D6),
                        focusedContainerColor = Color(0xFFD6D6D6),
                        focusedTextColor = Black,
                    )
                )
            }
        }
    }
}

@Composable
fun CategorySection() {
    val filterCategory: List<String> = listOf<String>("Starters", "Mains", "Desserts", "Drinks")
    Column(
        modifier = Modifier
            .padding(top = 10.dp, start = 10.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = stringResource(id = R.string.category_heading),
            fontWeight = FontWeight.ExtraBold,
        )
        LazyRow(
            modifier = Modifier
                .padding(vertical = 10.dp),
        ) {
            items(filterCategory) {categoryItem ->
                Button(
                    modifier = Modifier.padding(end = 10.dp),
                    onClick = {
                    }
                ) {
                    Text(
                        text = categoryItem
                    )
                }
            }
        }
        Divider(
            modifier = Modifier
                .padding(end = 10.dp)
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemLayout(title: String, description: String, price: String,imageSource: String) {
    Column(
        modifier = Modifier
            .padding(10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    color = Color(0xFF747272),
                    lineHeight = 16.sp,
                )
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp),
                    text = "$ $price",
                    fontWeight = FontWeight.Medium,
                )
            }
            GlideImage(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(70.dp),
                model = imageSource,
                contentDescription = title,
                contentScale = ContentScale.Crop,
            )
        }
        Divider(
            modifier = Modifier
                .padding(top = 10.dp)
        )
    }

}


@Preview(showSystemUi = true, apiLevel = 33)
@Composable
fun ProfileScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        HomeHeader(Modifier,{})
        Hero({})
        CategorySection()
//        MenuItemLayout(title = "Greek Salad", description = "This is indindan dish wihihc is very fa,ousd ssdgkhkasdgjh sdg", price = 20.0, imageResource = R.drawable.hero_image)
    }
}

