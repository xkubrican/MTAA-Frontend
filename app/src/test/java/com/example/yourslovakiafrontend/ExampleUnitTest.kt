package com.example.yourslovakiafrontend

import org.junit.Test
import com.example.yourslovakiafrontend.api_handler.ApiHandler
import fiit.mtaa.yourslovakia.models.AuthenticationRequest

class ExampleUnitTest {

    private val apiHandler = ApiHandler()
    private val authRequest = AuthenticationRequest("andrej@halo.sk", "andrejko123")

    @Test
    fun createUser() {
        apiHandler.register(authRequest)
    }

    @Test
    fun getTokenTest() {
        apiHandler.getToken(authRequest)
    }
}
