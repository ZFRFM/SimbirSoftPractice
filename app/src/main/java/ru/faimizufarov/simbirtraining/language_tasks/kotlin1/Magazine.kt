package ru.faimizufarov.simbirtraining.language_tasks.kotlin1

data class Magazine(
    override val price: Int,
    override val wordCount: Int,
) : Publication {
    override fun getType(): String {
        return "Magazine"
    }
}
