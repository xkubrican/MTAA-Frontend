package com.example.yourslovakiafrontend.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.yourslovakiafrontend.databinding.FragmentLoginBinding
import okhttp3.*
import java.io.IOException

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val emailEditText = binding.editTextEmail
        val passwordEditText = binding.editTextPassword
        val confirmPasswordEditText = binding.editTextConfirmPassword
        val loginButton = binding.buttonLogin

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (password == confirmPassword) {
                // Passwords match, proceed with login

                val url = "https://your-backend-url/createUser" // Replace with your actual backend URL
                val requestBody = FormBody.Builder()
                    .add("email", email)
                    .add("password", password)
                    .build()

                val request = Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build()

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                        // Handle failure, e.g., show error message
                        activity?.runOnUiThread {
                            Toast.makeText(requireContext(), "Failed to create user", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) {
                            // User created successfully, handle the response as needed
                            activity?.runOnUiThread {
                                Toast.makeText(requireContext(), "User created successfully", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // Server returned an error response, handle it accordingly
                            activity?.runOnUiThread {
                                Toast.makeText(requireContext(), "Failed to create user", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            } else {
                // Passwords don't match, show an error message
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}