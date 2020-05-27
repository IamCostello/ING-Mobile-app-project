package com.example.kotlinpostapi.views

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.kotlinpostapi.R
import com.example.kotlinpostapi.posts.PostAdapter
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PostListTest{

    @get: Rule
    val activityRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    @Test
    fun isLoadingPostListAsFirstFragment() {
        onView(withId(R.id.posts_view)).check(matches(isDisplayed()))
    }

    @Test
    fun isPostListNotEmpty() {
        onView(withId(R.id.posts_view)).perform(actionOnItemAtPosition<PostAdapter.PostsViewHolder>(3, click()))

    }

}
