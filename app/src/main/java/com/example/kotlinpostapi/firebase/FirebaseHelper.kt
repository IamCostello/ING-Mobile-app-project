package com.example.kotlinpostapi.firebase

import android.util.Log
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseHelper {

    companion object {

        internal var state: Boolean = false
        private var auth: FirebaseAuth? = null



        fun getInstance(): FirebaseAuth? {
            if(auth == null ){

                auth = Firebase.auth
            }
            return auth;
        }

        fun getCurrentUser(): FirebaseUser?{
            return getInstance()?.currentUser

        }

        fun login(email:String, password: String){
            getInstance()?.signInWithEmailAndPassword(email,password)?.addOnCompleteListener{
                if(it.isSuccessful){
                    return@addOnCompleteListener
                }
            }

        }

        fun register(email:String,password: String){
            getInstance()?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("tag", "createUserWithEmail:success")

                    } else{
                        Log.w("FAIL", "createUserWithEmail:failure", it.exception)


                    }


                }

            }
        fun signOut(){
            getInstance()?.signOut()
        }


    }
}