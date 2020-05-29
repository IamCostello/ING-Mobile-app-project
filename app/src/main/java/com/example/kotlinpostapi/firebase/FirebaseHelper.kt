package com.example.kotlinpostapi.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseHelper {

    companion object {

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


        fun signOut(){
            getInstance()?.signOut()
        }


    }
}