package com.example.kotlinpostapi.views

import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
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

@RunWith(AndroidJUnit4::class)
class PostListTest{

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
    fun isLoadingPostListAsFirstFragment() {
        onView(withId(R.id.loginEmail)).perform(click()).perform(ViewActions.typeText("example@example.com"))
        onView(withId(R.id.loginUserPassword)).perform(click()).perform(ViewActions.typeText("example"))
        pressBack()
        onView(withId(R.id.loginButton)).perform(click())
        onView(withId(R.id.posts_view)).check(matches(isDisplayed()))
    }

    @Test
    fun isPostListNotEmpty() {
        onView(withId(R.id.loginEmail)).perform(click()).perform(ViewActions.typeText("example@example.com"))
        onView(withId(R.id.loginUserPassword)).perform(click()).perform(ViewActions.typeText("example"))
        pressBack()
        onView(withId(R.id.loginButton)).perform(click())
        onView(withId(R.id.posts_view)).check(Helpers.countItems(greaterThan(1)))

    }

    @Test
    fun testNavigationToUserDetails(){
        onView(withId(R.id.loginEmail)).perform(click()).perform(ViewActions.typeText("example@example.com"))
        onView(withId(R.id.loginUserPassword)).perform(click()).perform(ViewActions.typeText("example"))
        pressBack()
        onView(withId(R.id.loginButton)).perform(click())
        onView(withId(R.id.posts_view)).perform(actionOnItemAtPosition<PostAdapter.PostsViewHolder>(3, Helpers.clickChildView(R.id.username)))

        onView(withId(R.id.album_icon)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationBackFromUserDetails() {
        onView(withId(R.id.loginEmail)).perform(click()).perform(ViewActions.typeText("example@example.com"))
        onView(withId(R.id.loginUserPassword)).perform(click()).perform(ViewActions.typeText("example"))
        pressBack()
        onView(withId(R.id.loginButton)).perform(click())
        onView(withId(R.id.posts_view)).perform(actionOnItemAtPosition<PostAdapter.PostsViewHolder>(3, Helpers.clickChildView(R.id.username)))

        onView(withId(R.id.album_icon)).check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.posts_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationToComments() {
        onView(withId(R.id.loginEmail)).perform(click()).perform(ViewActions.typeText("example@example.com"))
        onView(withId(R.id.loginUserPassword)).perform(click()).perform(ViewActions.typeText("example"))
        pressBack()
        onView(withId(R.id.loginButton)).perform(click())
        onView(withId(R.id.posts_view)).perform(actionOnItemAtPosition<PostAdapter.PostsViewHolder>(2, Helpers.clickChildView(R.id.show_comments_button)))

        onView(withId(R.id.comments_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationBackFromComments() {
        onView(withId(R.id.loginEmail)).perform(click()).perform(ViewActions.typeText("example@example.com"))
        onView(withId(R.id.loginUserPassword)).perform(click()).perform(ViewActions.typeText("example"))
        pressBack()
        onView(withId(R.id.loginButton)).perform(click())
        onView(withId(R.id.posts_view)).perform(actionOnItemAtPosition<PostAdapter.PostsViewHolder>(2, Helpers.clickChildView(R.id.show_comments_button)))

        onView(withId(R.id.comments_view)).check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.posts_view)).check(matches(isDisplayed()))

    }

    @Test
    fun testNavigationToMap(){
        onView(withId(R.id.loginEmail)).perform(click()).perform(ViewActions.typeText("example@example.com"))
        onView(withId(R.id.loginUserPassword)).perform(click()).perform(ViewActions.typeText("example"))
        pressBack()
        onView(withId(R.id.loginButton)).perform(click())
        onView(withId(R.id.posts_view)).perform(actionOnItemAtPosition<PostAdapter.PostsViewHolder>(5, Helpers.clickChildView(R.id.username)))

        onView(withId(R.id.map_icon)).check(matches(isDisplayed()))

        onView(withId(R.id.map_icon)).perform(click())

        onView(withId(R.id.mapView)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationBackFromMap(){
        onView(withId(R.id.loginEmail)).perform(click()).perform(ViewActions.typeText("example@example.com"))
        onView(withId(R.id.loginUserPassword)).perform(click()).perform(ViewActions.typeText("example"))
        pressBack()
        onView(withId(R.id.loginButton)).perform(click())

        onView(withId(R.id.posts_view)).perform(actionOnItemAtPosition<PostAdapter.PostsViewHolder>(5, Helpers.clickChildView(R.id.username)))

        onView(withId(R.id.map_icon)).check(matches(isDisplayed()))

        onView(withId(R.id.map_icon)).perform(click())

        onView(withId(R.id.mapView)).check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.map_icon)).check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.posts_view)).check(matches(isDisplayed()))

    }

}
