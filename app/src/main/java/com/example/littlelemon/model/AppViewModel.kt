package com.example.littlelemon.model

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.littlelemon.database.MenuDatabase
import com.example.littlelemon.database.MenuItem
import com.example.littlelemon.network.MenuItemNetwork
import com.example.littlelemon.repository.MenuNetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class UserCredentials(
    val firstName: String,
    val lastName: String,
    val email: String
)

class AppViewModel(
    private val sharedPreferences: SharedPreferences,
    private val menuNetworkRepository: MenuNetworkRepository,
    context: Context
) : ViewModel() {

    private val FIRST_NAME = "first_name"
    private val LAST_NAME = "last_name"
    private val EMAIL = "email"

    private val database by lazy { Room.databaseBuilder(context, MenuDatabase::class.java, "menu.db").build() }

    val menuItemsState: MutableStateFlow<List<MenuItemNetwork>> = MutableStateFlow(emptyList())


    val databaseMenuItem: MutableLiveData<List<MenuItem>> = MutableLiveData(emptyList())

    fun addUserCredentials(userCredentials: UserCredentials) {
        sharedPreferences.edit(true) {
            putString(FIRST_NAME, userCredentials.firstName)
        }
        sharedPreferences.edit(true) {
            putString(LAST_NAME, userCredentials.lastName)
        }
        sharedPreferences.edit(true) {
            putString(EMAIL, userCredentials.email)
        }
    }

    fun getUserData(): UserCredentials {
        return UserCredentials(
            sharedPreferences.getString(FIRST_NAME, "")!!,
            sharedPreferences.getString(LAST_NAME, "")!!,
            sharedPreferences.getString(EMAIL, "")!!,
        )
    }

    fun clearUserData() {
        sharedPreferences.edit(true) {
            putString(FIRST_NAME, "")
        }
        sharedPreferences.edit(true) {
            putString(LAST_NAME, "")
        }
        sharedPreferences.edit(true) {
            putString(EMAIL, "")
        }
    }

    fun isUserDataPresent(): Boolean {
        val user = getUserData()
        return (user.firstName.isNotEmpty() && user.lastName.isNotEmpty() && user.email.isNotEmpty())
    }

//    fun getFoodMenuNetwork(category: String) {
//        viewModelScope.launch {
//            menuItemsState.value =  menuNetworkRepository.getMenu(category)
//            storeMenuItemToDatabase()
//        }
//    }

    fun getFoodMenuLocal() {
        viewModelScope.launch {
            menuItemsState.value =  menuNetworkRepository.getMenu()
            storeMenuItemToDatabase()
            databaseMenuItem.value = database.menuDao().getAllMenuItems().value
        }
            Log.d("HOMETEST", "inside getfood ${databaseMenuItem.value}")
    }

    private fun storeMenuItemToDatabase() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                for (item in menuItemsState.value) {
                    database.menuDao().saveMenuItem(item.toDatabaseMenuItem())
                }
            }
        }
    }
}

class AppViewModelFactory(
    private val sharedPreferences: SharedPreferences,
    private val menuNetworkRepository: MenuNetworkRepository,
    private val viewModelContext: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(sharedPreferences, menuNetworkRepository, viewModelContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        fun getInstance(sharedPreferences: SharedPreferences, menuNetworkRepository: MenuNetworkRepository, context: Context): AppViewModelFactory {
            return AppViewModelFactory(sharedPreferences, menuNetworkRepository, context)
        }
    }
}