package com.example.kotlinpostapi.Users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinpostapi.apiObjects.User
import com.example.kotlinpostapi.repository.UserRepository
import com.example.kotlinpostapi.util.EspressoIdlingResource
import kotlinx.coroutines.launch
import com.example.kotlinpostapi.util.Result
import com.example.kotlinpostapi.util.ResultType
import java.util.logging.Logger

class UserViewModel(private val userRepository: UserRepository): ViewModel() {
    val userLiveData: MutableLiveData<User> = MutableLiveData()
    val isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getUserData(userId: Int){
        EspressoIdlingResource.increment()
        viewModelScope.launch {
            val response = userRepository.getUserList()
            val uiResult = Result(response.resultType, response.data?.get(userId - 1))

            updateUserLiveData(uiResult)
            EspressoIdlingResource.decrement()
        }
    }

    private fun updateUserLiveData(result: Result<User>){
        if(result.resultType == ResultType.SUCCESS){
            userLiveData.postValue(result.data)
            Logger.getAnonymousLogger().info("updateUserLiveViewData SUCCESS")
        }
        else{
            onError()
        }
    }
    private fun onError() = isErrorLiveData.postValue(true)
}