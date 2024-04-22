package com.example.yourslovakiafrontend.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourslovakiafrontend.api_handler.ApiHandler
import fiit.mtaa.yourslovakia.models.AuthenticationRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val authRequest = AuthenticationRequest(email, password)
            val authResponse = ApiHandler.getToken(authRequest)
            withContext(Dispatchers.Main) {
                onResult(authResponse != null && authResponse.accessToken.isNotEmpty() && authResponse.refreshToken.isNotEmpty())
                if (authResponse != null) {
                    ApiHandler.setTokens(authResponse.accessToken, authResponse.refreshToken)
                }
            }
        }
    }

}
