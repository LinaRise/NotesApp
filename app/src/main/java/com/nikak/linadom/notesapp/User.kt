package com.nikak.linadom.notesapp

data class User(val id: Long, val login: String, val password: String) {
    constructor(nameInput: String, passwordInput: String) : this(0,nameInput,passwordInput)
}