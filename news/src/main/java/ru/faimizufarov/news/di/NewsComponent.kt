package ru.faimizufarov.news.di

import ru.faimizufarov.news.NewsFragment
import javax.inject.Singleton

@Singleton
interface NewsComponent {
    fun injectNewsFragment(newsFragment: NewsFragment)
}
