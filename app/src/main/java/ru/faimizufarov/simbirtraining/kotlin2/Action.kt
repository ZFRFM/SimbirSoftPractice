package ru.faimizufarov.simbirtraining.kotlin2

sealed class Action {
    class Login(user: User): Action()
    class Logout: Action()
    class Registration: Action()
}