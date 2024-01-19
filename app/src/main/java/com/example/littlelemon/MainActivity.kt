package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.model.AppViewModel
import com.example.littlelemon.model.AppViewModelFactory
import com.example.littlelemon.navigation.Navigation
import com.example.littlelemon.repository.MenuNetworkRepository
import com.example.littlelemon.ui.theme.LittleLemonTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val sharedPreferences by lazy { getSharedPreferences("user-data", MODE_PRIVATE) }

                    val viewModel: AppViewModel = ViewModelProvider(this, AppViewModelFactory.getInstance(sharedPreferences, MenuNetworkRepository(), applicationContext))
                        .get(AppViewModel::class.java)

                    Navigation(
                        navController = navController,
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {

}