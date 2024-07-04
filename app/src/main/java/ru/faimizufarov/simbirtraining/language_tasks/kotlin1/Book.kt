package ru.faimizufarov.simbirtraining.language_tasks.kotlin1

class Book(
    override val price: Int,
    override val wordCount: Int,
) : Publication {
    override fun getType(): String {
        return if (wordCount in 0 until 1000) {
            "Flash Fiction"
        } else if (wordCount in 1000 until 7500) {
            "Short Story"
        } else if (wordCount > 7500) {
            "Novel"
        } else {
            throw Exception("Negative number of pages is not acceptable")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Book) return false
        return (price == other.price) && (wordCount == other.wordCount)
    }

    override fun hashCode(): Int {
        var result = price
        result = 31 * result + wordCount
        return result
    }
}
