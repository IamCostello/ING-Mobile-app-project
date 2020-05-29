package com.example.kotlinpostapi.views

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
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
import org.hamcrest.Matchers.greaterThan
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class AlbumListTest{

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun testIsDisplayingAlbumList(){

        onView(withId(R.id.posts_view)).perform(actionOnItemAtPosition<PostAdapter.PostsViewHolder>(4, Helpers.clickChildView(R.id.username)))

        onView(withId(R.id.album_icon)).perform(click())

        onView(withId(R.id.albums_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testAreListsNotEmpty() {


        for (i in listOf(1,11,21,31,41,51)){

            onView(withId(R.id.posts_view)).perform(actionOnItemAtPosition<PostAdapter.PostsViewHolder>(i, Helpers.clickChildView(R.id.username)))

            onView(withId(R.id.album_icon)).perform(click())

            onView(withId(R.id.albums_view)).check(Helpers.countItems(greaterThan(1)))

            pressBack()
            pressBack()
        }

    }


}