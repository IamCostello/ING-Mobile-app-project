package com.example.kotlinpostapi.firebase

import android.os.Bundle
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


class Login : Fragment(), Navigation.OnLogInClickListener, Navigation.OnMoveToRegisterClickListener {
    private lateinit var binding:FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var email:String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        binding.loginButton.setOnClickListener(View.OnClickListener { onLogInClick() })
        binding.noAccountTextView.setOnClickListener(View.OnClickListener { onMoveToRegisterClick() })


        return binding.root
    }

    override fun onLogInClick() {

        email = binding.loginEmail.text.toString()
        password = binding.loginUserPassword.text.toString()


        if(email.isNotEmpty() && password.isNotEmpty() && password.length >= 6) {
           auth.signInWithEmailAndPassword(email,password)
               .addOnCompleteListener{
                   if(it.isSuccessful){
                       val action = LoginDirections.actionAuthLoginToPostList()

                       findNavController().navigate(action)
                   }
               }
        }


    }



    override fun onMoveToRegisterClick() {
        val action = LoginDirections.actionAuthLoginToAuthRegister()


        findNavController().navigate(action)
    }

}