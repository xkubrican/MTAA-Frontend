package com.example.yourslovakiafrontend.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourslovakiafrontend.api_handler.ApiHandler
import fiit.mtaa.yourslovakia.models.AuthenticationRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel : ViewModel() {


    fun validateRegistration(email: String, password: String, confirmPassword: String): Boolean {
        if (!email.contains("@")) {
            return false
        }
        if (password != confirmPassword) {
            return false
        }
        return true
    }


    fun register(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val authRequest = AuthenticationRequest(email, password)
            val registrationSuccess = ApiHandler.register(authRequest)
            if (registrationSuccess) {
                val authResponse = ApiHandler.getToken(authRequest)
                withContext(Dispatchers.Main) {
                    if (authResponse != null && authResponse.accessToken.isNotEmpty() && authResponse.refreshToken.isNotEmpty()) {
                        ApiHandler.setTokens(authResponse.accessToken, authResponse.refreshToken)
                        onResult(true)
                    } else {
                        onResult(false)
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    onResult(false)
                }
            }
        }
    }


}
