package com.example.kotlinpostapi.views

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.kotlinpostapi.Helpers
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

        onView(withId(R.id.posts_view)).perform(actionOnItemAtPosition<PostAdapter.PostsViewHolder>(2, Helpers.clickChildView(R.id.username)))

        onView(withId(R.id.name_text)).check(matches(isDisplayed()))
        onView(withId(R.id.email_text)).check(matches(isDisplayed()))
        onView(withId(R.id.company_text)).check(matches(isDisplayed()))
        onView(withId(R.id.phone_text)).check(matches(isDisplayed()))
        onView(withId(R.id.web_text)).check(matches(isDisplayed()))
    }

}