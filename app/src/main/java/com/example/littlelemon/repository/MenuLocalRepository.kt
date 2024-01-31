package com.example.littlelemon.repository

import com.example.littlelemon.database.MenuDao
import com.example.littlelemon.database.MenuItem
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getAllItemsStream(): Flow<List<MenuItem>>

    suspend fun insertItem(item: MenuItem)

    suspend fun deleteItem(item: MenuItem)
}

class MenuLocalRepository(private val menuDao: MenuDao): MenuRepository {
    override fun getAllItemsStream(): Flow<List<MenuItem>> {
       return menuDao.getAllMenuItems()
    }

    override suspend fun insertItem(item: MenuItem) {
        menuDao.saveMenuItem(item)
    }

    override suspend fun deleteItem(item: MenuItem) {
        menuDao.deleteMenuItem(item)
    }

}