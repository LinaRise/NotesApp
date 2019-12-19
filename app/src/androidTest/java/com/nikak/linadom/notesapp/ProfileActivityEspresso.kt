package com.nikak.linadom.notesapp

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.CoreMatchers.anything
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ProfileActivityEspresso {
    @get:Rule
    var mActivityRule = ActivityTestRule<ProfileActivity>(ProfileActivity::class.java)

    @Rule
    @JvmField
    var signInActivity = ActivityTestRule<SignInActivity>(SignInActivity::class.java)

    @Rule
    @JvmField
    var addNotesActivity = ActivityTestRule<AddNotesActivity>(AddNotesActivity::class.java)

    @Before
    @Throws(Exception::class)
    fun setUp() {
        Intents.init()
    }

    @After
    fun cleanUp() {
        Intents.release()
    }

    @Test
    @Throws(Exception::class)
    fun exit() {
        Espresso.onView(withId(R.id.exit)).perform(click())
        Intents.intended(IntentMatchers.hasComponent(SignInActivity::class.java.name))
    }

    @Test
    @Throws(Exception::class)
    fun callAddNoteActivity() {
        Espresso.onView(withId(R.id.addNote)).perform(click())
        Intents.intended(IntentMatchers.hasComponent(AddNotesActivity::class.java.name))
    }

    @Test
    @Throws(Exception::class)
    fun selectItemOnListView() {
        onData(anything()).inAdapterView(withId(R.id.notesListView)).atPosition(2).perform(click())
    }


}
