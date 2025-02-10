package com.contacts.desafio.android

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.contacts.desafio.android.RecyclerViewMatchers.atPosition
import com.contacts.desafio.android.presentation.MainActivity
import com.list.desafio.android.R

class MainActivityRobot {

    fun launch() {
        launchActivity<MainActivity>().apply {
            moveToState(Lifecycle.State.RESUMED)
        }
    }

    fun checkTitleIsDisplayed(title: String) {
        onView(withText(title)).check(matches(isDisplayed()))
    }

    fun checkItem(name: String, username: String, position: Int) {
        onView(withText(name)).check(matches(isDisplayed()))
        onView(withText(username)).check(matches(isDisplayed()))

        onView(withId(R.id.recyclerView)).check(
            matches(atPosition(position, hasDescendant(withId(R.id.picture))))
        )
    }
}

fun mainActivityRobot(block: MainActivityRobot.() -> Unit) {
    MainActivityRobot().apply(block)
}

