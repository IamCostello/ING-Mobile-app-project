package com.example.kotlinpostapi.util

import com.example.kotlinpostapi.apiObjects.User

class UsernameParser {
    companion object{
        fun getUsernames(result: Result<List<User>>): Result<List<String>> {
            val userList: List<String> = result.data!!.map{ it.name!! }
            return Result(result.resultType, userList)
        }
    }
}