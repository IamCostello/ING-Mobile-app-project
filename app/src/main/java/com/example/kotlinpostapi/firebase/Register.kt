package com.example.kotlinpostapi.firebase

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotlinpostapi.Navigation
import com.example.kotlinpostapi.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Register : Fragment(), Navigation.OnExistingUserClickListener,
    Navigation.OnRegisterClickListener {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var username: String
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.registerButton.setOnClickListener(View.OnClickListener { onRegisterClick() })
        binding.backToLogin.setOnClickListener(View.OnClickListener { onExistingClick() })
        return binding.root
    }

    override fun onExistingClick() {

        findNavController().navigate(RegisterDirections.actionAuthRegisterToAuthLogin5())
    }

    override fun onRegisterClick() {

        if (validateForm()) {
            //
            FirebaseHelper.getInstance()?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        findNavController().navigate(RegisterDirections.actionAuthLoginToPostList())
                        Log.d("tag", "createUserWithEmail:success")

                    } else{
                        Toast.makeText(activity,"User already exist",Toast.LENGTH_LONG).show()
                        Log.w("FAIL", "createUserWithEmail:failure", it.exception)


                    }


                }

        }




    }

    private fun validateForm(): Boolean {
        var valid = true


        username = binding.registerUsername.text.toString()
        if (TextUtils.isEmpty(username) || username.length < 6) {
            binding.registerUsername.error = "Required, at least 6 characters."
            valid = false
        } else {
            binding.registerUsername.error = null
        }


        email = binding.registerUserEmail.text.toString()
        if (TextUtils.isEmpty(email) || (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            binding.registerUserEmail.error = "Required."
            valid = false
        } else {
            binding.registerUserEmail.error = null
        }

        password = binding.registerUserPassword.text.toString()
        if (TextUtils.isEmpty(password) || password.length < 6) {
            binding.registerUserPassword.error = "Required, at least 6 characters."
            valid = false
        } else {
            binding.registerUserPassword.error = null
        }

        return valid
    }
}