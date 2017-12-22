package com.app.lyasin.starwarsapp

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.app.lyasin.starwarsapp.activity.MainActivity
import com.app.lyasin.starwarsapp.model.Character
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityUITest {
    @Rule @JvmField
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java, false, false)

    @Test
    fun whenActivityIsLaunchedButtonIsDisplayed() {
        activityRule.launchActivity(null)
        onView(withId(R.id.fab_qr_code)).check(matches(isDisplayed()))
    }

    @Test
    fun whenActivityIsLaunchedShowSavedData() {
        val c  = Character()
        c.name = "test"
        activityRule.launchActivity(null)
        activityRule.activity.charactersList.clear()
        activityRule.activity.charactersList.add(c)

        activityRule.runOnUiThread { activityRule.activity.updateView() }

        onView(withId(R.id.tv_character_name)).check(matches(withText("test")))
    }

}
