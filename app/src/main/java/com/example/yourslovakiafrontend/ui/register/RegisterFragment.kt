package com.example.yourslovakiafrontend.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.yourslovakiafrontend.R
import com.example.yourslovakiafrontend.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        val emailEditText = binding.editTextEmail
        val passwordEditText = binding.editTextPassword
        val loginButton = binding.buttonRegister
        val alreadyRegisteredButton = binding.buttonAlreadyRegistered

        loginButton.setOnClickListener {
            // Handle login logic here
        }

        alreadyRegisteredButton.setOnClickListener {
            // Navigate to another screen (assuming you have a navigation component)
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}