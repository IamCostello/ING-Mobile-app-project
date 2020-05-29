package com.example.kotlinpostapi.util

import com.example.kotlinpostapi.apiObjects.Comment
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.apiObjects.User

class UiParser {
    companion object{
        fun getUsernames(result: Result<List<User>>): List<String> {
            return result.data!!.map{ it.username!! }
        }

        fun getCommentsCount(postList: List<Post>?, result: Result<List<Comment>>): List<Int> {
            val commentAmountList: MutableList<Int> = mutableListOf()

            postList?.forEach { element ->
                run {
                    commentAmountList.add(result.data?.count { it.postId == element.id } ?: 0)
                }
            }

            return commentAmountList
        }
    }
}