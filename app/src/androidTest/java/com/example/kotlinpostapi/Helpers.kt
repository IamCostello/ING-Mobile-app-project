package com.example.kotlinpostapi

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matcher

class Helpers{
    companion object{
        fun clickChildView(id: Int) : ViewAction {
            return object: ViewAction {
                override fun getDescription(): String {
                    return "Click on the child with specified ID"
                }

                override fun getConstraints(): Matcher<View>? {
                    return null
                }

                override fun perform(uiController: UiController?, view: View?) {
                    val v : View? = view?.findViewById(id)
                    v?.performClick()
                }

            }
        }

        fun countItems(matcher : Matcher<Int>) : ViewAssertion {
            return ViewAssertion { view, noViewFoundException ->
                if(noViewFoundException != null)
                    throw noViewFoundException

                val recyclerview: RecyclerView = view as RecyclerView
                val adapter = recyclerview.adapter
                ViewMatchers.assertThat(adapter?.itemCount, matcher)
            }
        }

    }
}
