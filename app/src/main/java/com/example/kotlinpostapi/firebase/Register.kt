package com.example.kotlinpostapi.firebase

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotlinpostapi.Navigation
import com.example.kotlinpostapi.databinding.FragmentRegisterBinding


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
                        binding.registerUserEmail.onEditorAction(EditorInfo.IME_ACTION_DONE)
                        binding.registerUserPassword.onEditorAction(EditorInfo.IME_ACTION_DONE)
                        binding.registerUsername.onEditorAction(EditorInfo.IME_ACTION_DONE)
                        findNavController().navigate(RegisterDirections.actionAuthLoginToPostList())


                    } else{
                        Toast.makeText(activity,"User already exist",Toast.LENGTH_LONG).show()



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