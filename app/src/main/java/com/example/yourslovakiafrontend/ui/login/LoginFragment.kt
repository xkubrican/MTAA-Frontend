package com.example.yourslovakiafrontend.ui.login;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.yourslovakiafrontend.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

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
