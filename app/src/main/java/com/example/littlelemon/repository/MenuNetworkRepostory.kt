package com.example.littlelemon.repository

import com.example.littlelemon.network.MenuItemNetwork
import com.example.littlelemon.network.MenuNetwork
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class MenuNetworkRepository {

    private val client: HttpClient = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("text","plain"))
            }
        }

    suspend fun getMenu(): List<MenuItemNetwork> {
        val response: String = client
            .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body()
        val json = Json { ignoreUnknownKeys = true }
        val menuNetwork: MenuNetwork = json.decodeFromString(response)
        return menuNetwork.menu
    }
}