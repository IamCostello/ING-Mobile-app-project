package com.example.kotlinpostapi.firebase

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotlinpostapi.Navigation
import com.example.kotlinpostapi.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import timber.log.Timber


class Login : Fragment(), Navigation.OnLogInClickListener,
    Navigation.OnMoveToRegisterClickListener {
    private lateinit var binding: FragmentLoginBinding

    private lateinit var email: String
    private lateinit var password: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseHelper.signOut()
        if(FirebaseHelper.getCurrentUser() != null){
            findNavController().navigate(LoginDirections.actionAuthLoginToPostList())
        }
    }

    override fun onStart() {
        super.onStart()

        //findNavController().navigate(LoginDirections.actionAuthLoginToPostList())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.loginButton.setOnClickListener(View.OnClickListener { onLogInClick() })
        binding.noAccountTextView.setOnClickListener(View.OnClickListener { onMoveToRegisterClick() })


        return binding.root
    }


    override fun onLogInClick() {

        if (validateForm()) {
            Log.d("asa","i tak dziala nobek +" + validateForm())
            FirebaseHelper.login(email, password)
            if(FirebaseHelper.getCurrentUser() != null){
                findNavController().navigate(LoginDirections.actionAuthLoginToPostList())

            }
        }


    }


    override fun onMoveToRegisterClick() {
        val action = LoginDirections.actionAuthLoginToAuthRegister()


        findNavController().navigate(action)
    }

    private fun validateForm(): Boolean {
        var valid = true

        email = binding.loginEmail.text.toString()
        if (TextUtils.isEmpty(email) || (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            binding.loginEmail.error = "Required."
            valid = false
        } else {
            binding.loginEmail.error = null
        }

        password = binding.loginUserPassword.text.toString()
        if (TextUtils.isEmpty(password) || password.length < 6) {
            binding.loginUserPassword.error = "Required, at least 6 characters."
            valid = false
        } else {
            binding.loginUserPassword.error = null
        }

        return valid
    }

}