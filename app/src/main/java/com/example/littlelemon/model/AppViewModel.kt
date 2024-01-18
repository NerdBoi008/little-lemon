package com.example.littlelemon.model

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class UserCredentials(
    val firstName: String,
    val lastName: String,
    val email: String
)

class AppViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    private val FIRST_NAME = "first_name"
    private val LAST_NAME = "last_name"
    private val EMAIL = "email"

    private val _userCredentials = MutableStateFlow<UserCredentials>(UserCredentials("","",""))
    val userCredentials: StateFlow<UserCredentials> = _userCredentials

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
}

class AppViewModelFactory(private val sharedPreferences: SharedPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(sharedPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        fun getInstance(sharedPreferences: SharedPreferences): AppViewModelFactory {
            return AppViewModelFactory(sharedPreferences)
        }
    }
}