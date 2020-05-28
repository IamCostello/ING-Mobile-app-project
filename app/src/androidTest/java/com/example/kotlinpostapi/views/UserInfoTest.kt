package com.example.kotlinpostapi.views

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.kotlinpostapi.MyMatchers
import com.example.kotlinpostapi.R
import com.example.kotlinpostapi.posts.PostAdapter
import com.example.kotlinpostapi.util.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserInfoTest{


    @get: Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)


    @Before
    fun registerIdlingResource(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun testUserIsDisplaying(){
        onView(withId(R.id.posts_view)).perform(actionOnItemAtPosition<PostAdapter.PostsViewHolder>(2, MyMatchers.clickChildView(R.id.username)))

        onView(withId(R.id.userEmail)).check(matches(isDisplayed()))
        onView(withId(R.id.userCity)).check(matches(isDisplayed()))
        onView(withId(R.id.userCompany)).check(matches(isDisplayed()))
        onView(withId(R.id.userPhone)).check(matches(isDisplayed()))
        onView(withId(R.id.userWebsite)).check(matches(isDisplayed()))
    }

}