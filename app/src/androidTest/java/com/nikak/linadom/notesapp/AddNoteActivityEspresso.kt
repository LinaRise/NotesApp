package com.nikak.linadom.notesapp

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v4.content.ContextCompat.startActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddNoteActivityEspresso {
    @get:Rule
    var mActivityRule = ActivityTestRule<AddNotesActivity>(
        AddNotesActivity::class.java
    )

    @Rule  @JvmField
    var intentActivity = ActivityTestRule<ProfileActivity>(ProfileActivity::class.java)

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
    fun addNewNote() {
        Espresso.onView(ViewMatchers.withId(R.id.editTitle)).perform(ViewActions.typeText("new title"))
        Espresso.onView(ViewMatchers.withId(R.id.editDes)).perform(ViewActions.typeText("new description")).perform(closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.addBtn)).perform(ViewActions.click())
        intended(hasComponent(ProfileActivity::class.java.name))
    }
}

