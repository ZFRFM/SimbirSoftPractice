package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization

sealed class NavigationCommand {
    data object ToMainActivity : NavigationCommand()

    data object FinishActivity : NavigationCommand()
}
