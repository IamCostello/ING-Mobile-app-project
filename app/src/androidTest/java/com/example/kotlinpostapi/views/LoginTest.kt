package com.example.kotlinpostapi.views



import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
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
import org.hamcrest.Matchers.greaterThan
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class LoginTest {

    @get: Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
    @Test
    fun testLoginDisplayed() {

        onView(withId(R.id.loginEmail)).perform(click()).perform(typeText("example@example.com"))
        onView(withId(R.id.loginUserPassword)).perform(click()).perform(typeText("example"))
        pressBack()
        onView(withId(R.id.loginButton)).perform(click())


        onView(withId(R.id.posts_view)).check(matches(isDisplayed()))

    }
}