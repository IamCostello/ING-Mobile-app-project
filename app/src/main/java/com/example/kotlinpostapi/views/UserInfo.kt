package com.example.kotlinpostapi.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.kotlinpostapi.Users.UserViewModel
import com.example.kotlinpostapi.apiObjects.User
import com.example.kotlinpostapi.databinding.FragmentUserInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserInfo : Fragment() {
    private val userViewModel: UserViewModel by viewModel()
    private lateinit var binding: FragmentUserInfoBinding
    private lateinit var userData: User
    val args: UserInfoArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserInfoBinding.inflate(inflater, container, false)

        observeLiveData()
        getUserData(args.userId)

        return binding.root
    }

    private fun observeLiveData() {
        userViewModel.isErrorLiveData.observe(viewLifecycleOwner, Observer { onReceivedError() })
        userViewModel.userLiveData.observe(viewLifecycleOwner, Observer { onUserDataReceived(it) })
    }

    private fun onReceivedError() {
        android.app.AlertDialog.Builder(activity).setTitle("Błąd").setCancelable(false)
            .setNegativeButton("Anuluj") { _,
                                           _ ->
                activity?.finish()
            }.setPositiveButton("Spróbuj ponownie") { _, _ -> getUserData(args.userId) }.show()
    }

    private fun onUserDataReceived(userData: User){
        this.userData = userData
        binding.user = userData
    }

    private fun getUserData(userId: Int){
        userViewModel.getUserData(userId)
    }
}