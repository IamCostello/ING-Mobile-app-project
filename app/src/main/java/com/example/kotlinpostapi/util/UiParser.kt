package com.example.kotlinpostapi.util

import com.example.kotlinpostapi.apiObjects.Comment
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.apiObjects.User

class UiParser {
    companion object{
        fun getUsernames(result: Result<List<User>>): Result<List<String>> {
            val userList: List<String> = result.data!!.map{ it.name!! }
            return Result(result.resultType, userList)
        }

        fun getCommentsCount(postList: List<Post>?, result: Result<List<Comment>>): Result<List<Int>> {
            val commentAmountList: MutableList<Int> = mutableListOf()

            postList?.forEach { element ->
                run {
                    commentAmountList.add(result.data!!.count { it.postId == element.id })
                }
            }

            return Result(result.resultType, commentAmountList)
        }
    }
}