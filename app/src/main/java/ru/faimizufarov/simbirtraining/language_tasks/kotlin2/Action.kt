package ru.faimizufarov.simbirtraining.language_tasks.kotlin2

sealed class Action {
    class Login(user: User) : Action()

    class Logout : Action()

    class Registration : Action()
}
