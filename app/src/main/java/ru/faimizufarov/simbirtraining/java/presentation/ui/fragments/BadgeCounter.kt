package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import io.reactivex.rxjava3.subjects.PublishSubject

object BadgeCounter {
    val badgeCounter = PublishSubject.create<Int>()
}
