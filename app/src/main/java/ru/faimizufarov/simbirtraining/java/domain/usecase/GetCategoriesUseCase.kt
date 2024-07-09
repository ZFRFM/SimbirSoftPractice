package ru.faimizufarov.simbirtraining.java.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.faimizufarov.simbirtraining.java.domain.repository.CategoryRepository

class GetCategoriesUseCase(private val categoryRepository: CategoryRepository) {
    suspend fun execute() =
        withContext(Dispatchers.IO) {
            categoryRepository.getCategoryList()
        }
}
