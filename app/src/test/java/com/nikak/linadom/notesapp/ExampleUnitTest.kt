package com.nikak.linadom.notesapp

import junit.framework.Assert.assertNotNull
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val activity: SignInActivity = SignInActivity()
    var dbHandler: DatabaseHandler? = null

    @Test
    fun checkDBConnection() {
        dbHandler = DatabaseHandler(activity)
        assertNotNull(dbHandler)

    }


}



















