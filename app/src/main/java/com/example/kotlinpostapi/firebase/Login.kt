package com.example.kotlinpostapi.firebase

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotlinpostapi.Navigation
import com.example.kotlinpostapi.databinding.FragmentLoginBinding
import com.example.kotlinpostapi.util.EspressoIdlingResource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class Login : Fragment(), Navigation.OnLogInClickListener,
    Navigation.OnMoveToRegisterClickListener {
    private lateinit var binding: FragmentLoginBinding

    private lateinit var email: String
    private lateinit var password: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomNavigationView?.visibility = View.GONE

        if(FirebaseHelper.getCurrentUser() != null){
            findNavController().navigate(LoginDirections.actionAuthLoginToPostList())
        }

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
            EspressoIdlingResource.increment()
            FirebaseHelper.getInstance()?.signInWithEmailAndPassword(email,password)?.addOnCompleteListener{
                if(it.isSuccessful){
                    EspressoIdlingResource.decrement()

                    binding.loginEmail.onEditorAction(EditorInfo.IME_ACTION_DONE)
                    binding.loginUserPassword.onEditorAction(EditorInfo.IME_ACTION_DONE)
                    findNavController().navigate(LoginDirections.actionAuthLoginToPostList())
                    return@addOnCompleteListener

                }
                else{
                    EspressoIdlingResource.decrement()
                    Toast.makeText(activity,"Incorrect data", Toast.LENGTH_LONG).show()

                }
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