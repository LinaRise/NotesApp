package com.nikak.linadom.notesapp

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SignInActivityEspresso {
    @get:Rule
    var mActivityRule = ActivityTestRule<SignInActivity>(
        SignInActivity::class.java
    )

    @Rule  @JvmField
    var intentActivity = IntentsTestRule<ProfileActivity>(ProfileActivity::class.java)

    @Before
    @Throws(Exception::class)
    fun setUp() {

    }

    @Test
    @Throws(Exception::class)
    fun onSignIn() {
        onView(withId(R.id.userNameField)).perform(typeText("login"))
        onView(withId(R.id.passwordField)).perform(typeText("password"))
        onView(withId(R.id.btn_signIn)).perform(click())
        intended(hasComponent(ProfileActivity::class.java.name))
    }
}

