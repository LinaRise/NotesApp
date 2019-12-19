package com.nikak.linadom.notesapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_in.*
import android.content.Intent


class SignInActivity : AppCompatActivity() {

    var dbHandler: DatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        dbHandler = DatabaseHandler(this)


        btn_signIn.setOnClickListener {
            signIn(userNameField.text.toString(), passwordField.text.toString())
        }

    }


    fun signIn(userNameInput: String, passwordInput: String) {

        if (isPasswordValid(passwordInput) && isUserNameValid(userNameInput)) {
            val user = User(userNameInput, passwordInput)
            if (dbHandler!!.checkIfUserPresent(user)) {
                Toast.makeText(this, "Successfully signed in", Toast.LENGTH_LONG).show()
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                finish()
            } else Toast.makeText(this, "No such user in db", Toast.LENGTH_LONG).show()
        } else {
            if (!isPasswordValid(passwordInput)) {
                Toast.makeText(this, "Try again", Toast.LENGTH_LONG).show()
                passwordField.startAnimation(shakeError())
                passwordField.requestFocus()
            }
            if (!isUserNameValid(userNameInput)) {
                Toast.makeText(this, "Try again", Toast.LENGTH_LONG).show()

                userNameField.startAnimation(shakeError())
                userNameField.requestFocus()

            }
            if (!isUserNameValid(userNameInput) && !isPasswordValid(passwordInput)) {
                Toast.makeText(this, "Try again", Toast.LENGTH_LONG).show()
                userNameField.requestFocus()
                passwordField.startAnimation(shakeError())
                userNameField.startAnimation(shakeError())


            }
        }

    }


    //проверяет пароль
    fun isPasswordValid(password: String): Boolean {
        return password.length > 4 && password.matches("^[a-zA-Z0-9_]+\$".toRegex()) //&& !TextUtils.isEmpty(password)
    }

    //проверяет логин
    fun isUserNameValid(userName: String): Boolean {
        return userName.length > 4 && userName.matches("^[a-zA-Z0-9_@]+\$".toRegex())
    }

    //встряска элемента
    fun shakeError(): TranslateAnimation {
        val shake = TranslateAnimation(0f, 10f, 0f, 0f)
        shake.duration = 500
        shake.interpolator = CycleInterpolator(7f)
        return shake
    }


}
