package com.example.littlelemon.network

import com.example.littlelemon.database.MenuItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuItemNetwork(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("price") val price: String,
    @SerialName("image") val image: String,
    @SerialName("category") val category: String
) {
    fun toDatabaseMenuItem(): MenuItem {
        return MenuItem(
            id = this.id,
            title = this.title,
            description = this.description,
            price = this.price,
            image = this.image,
            category = this.category
        )
    }
}

@Serializable
data class MenuNetwork(
    @SerialName("menu") val menu: List<MenuItemNetwork>
)