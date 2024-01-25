package com.example.travlingfocus.Login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.travlingfocus.data.UserRepository
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

class AuthViewModel (
    private val userRepository: UserRepository
) : ViewModel()
{
    var authUiState = mutableStateOf(AuthUiState())
        private set

    var loginUIState = mutableStateOf(LoginUIState())
        private set


    fun login() {
        val email = loginUIState.value.loginemail
        val password = loginUIState.value.loginpassword
        Log.d("AuthViewModel", "login: email: $email, password: $password")
       viewModelScope.launch {
             userRepository.getUserByEmail(email).collect{
                Log.d("AuthViewModel", "login: user: ${it.toString()}")
                if (it != null) {
                    if (it.password == password) {
                        Log.d("AuthViewModel", "login: user: ${it.id}")
                        authUiState.value = authUiState.value.copy(

                            userId = it.id,
                            isLogined = true,
                        )
                    } else {
                        loginUIState.value = loginUIState.value.copy(
                            loginError = "Wrong Password",
                        )
                    }
                } else {
                    loginUIState.value = loginUIState.value.copy(
                        loginError = "User not found",
                    )
                }
            }

        }
    }

    fun updateLoginState (email: String, password: String) {
        loginUIState.value = loginUIState.value.copy(
            loginemail = email,
            loginpassword = password,
        )
    }

    fun updateUiState (email: String, password: String, username: String) {
        authUiState.value = authUiState.value.copy(
            signupemail = email,
            signuppassword = password,
            signupusername = username,
        )
    }

    suspend fun saveUser() {
        val user = userRepository.getUserByEmail(authUiState.value.signupemail).asLiveData().value
        if (user == null) {
            userRepository.insertUser(
                com.example.travlingfocus.data.User(
                    username = authUiState.value.signupusername,
                    email = authUiState.value.signupemail,
                    password = authUiState.value.signuppassword,
                )
            )

            userRepository.getUserByEmail(authUiState.value.signupemail).collect{
                    authUiState.value = authUiState.value.copy(
                        userId = it!!.id,
                        isLogined = true,
                    )
            }
        }
    }
}

data class LoginUIState(
    var loginemail: String = "",
    var loginpassword: String = "",
    var loginError: String = "",
)

data class AuthUiState (
    val signupemail: String = "",
    val signuppassword: String = "",
    val signupusername: String = "",
    val userId: Int = 0,
    val isLogined: Boolean = false,
)
