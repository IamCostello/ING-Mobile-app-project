package com.example.kotlinpostapi.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.kotlinpostapi.Navigation
import com.example.kotlinpostapi.R
import com.example.kotlinpostapi.firebase.FirebaseHelper
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setupWithNavController(navhost.findNavController())
        navhost.findNavController().addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id) {
                R.id.authLogin -> bottomNavigationView.visibility = View.GONE
                R.id.authRegister -> bottomNavigationView.visibility = View.GONE
                else -> bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }


}


