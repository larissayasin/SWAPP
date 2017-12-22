package com.app.lyasin.starwarsapp

import android.content.Context
import android.content.Intent
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.app.lyasin.starwarsapp.activity.CharacterActivity
import org.hamcrest.CoreMatchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CharacterActivityUITest {
    var context : Context? = null
    @Rule @JvmField
    val activityRule: ActivityTestRule<CharacterActivity> = ActivityTestRule(CharacterActivity::class.java, false, false)

    @Before
    fun getContext(){
         context = getInstrumentation().targetContext

    }

    @Test
    fun whenActivityIsLaunchedQrCodeIsValid() {
        val intent = Intent()
        intent.putExtra(context!!.getString(R.string.from_qrcode), "https://swapi.co/api/people/14/")
        activityRule.launchActivity(intent)
        Thread.sleep(5000)
        onView(withId(R.id.tv_character_details_name)).check(matches(ViewMatchers.withText(containsString("Han Solo"))))
    }

    @Test
    fun whenActivityIsLaunchedQrCodeIsNotValid() {
        val intent = Intent()
        intent.putExtra(context!!.getString(R.string.from_qrcode), "http://www.google.com")
        activityRule.launchActivity(intent)

       // Thread.sleep(2000)
        onView(withText(context!!.getString(R.string.error_message))).inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView)))).check(matches(isDisplayed()))
    }

}
