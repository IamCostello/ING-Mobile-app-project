package com.example.kotlinpostapi.firebase

import android.os.Bundle
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

class Register : Fragment(),Navigation.OnExistingUserClickListener, Navigation.OnRegisterClickListener {
    private lateinit var auth:FirebaseAuth
    private lateinit var binding:FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        binding.registerButton.setOnClickListener(View.OnClickListener { onRegisterClick() })
        binding.backToLogin.setOnClickListener(View.OnClickListener { onExistingClick() })
        return binding.root
    }

    override fun onExistingClick() {
       findNavController().navigate(RegisterDirections.actionAuthRegisterToAuthLogin5())
    }

    override fun onRegisterClick() {
        val email = binding.registerUserEmail.text.toString()
        val password = binding.registerUserPassword.text.toString()


        Log.d("Login ","Email is" + email)
        Log.d("Password ","Password is" + password)


        if(email.isNotEmpty() && password.isNotEmpty() && password.length >= 6) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) return@addOnCompleteListener
                    Log.d("proba", "Created or no ${it.result!!.user!!.uid}")
                }
                
        }
    }

}