package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import io.reactivex.rxjava3.subjects.PublishSubject

object BadgeCountSubject {
    val badgeCountSubject = PublishSubject.create<Int>()
}
