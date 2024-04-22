package com.example.yourslovakiafrontend.ui.register

import androidx.lifecycle.ViewModel
import com.example.yourslovakiafrontend.api_handler.ApiHandler
import fiit.mtaa.yourslovakia.models.AuthenticationRequest

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

    fun register(email: String, password: String) {
        val authRequest = AuthenticationRequest(email, password)
        ApiHandler.register(authRequest)
        val authResponse = ApiHandler.getToken(authRequest)
    }
}
