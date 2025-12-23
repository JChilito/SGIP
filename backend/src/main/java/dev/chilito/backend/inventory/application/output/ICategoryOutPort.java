package dev.chilito.backend.inventory.application.output;

import java.util.List;
import java.util.Optional;

import dev.chilito.backend.inventory.domain.models.Category;

public interface ICategoryOutPort {
    Category createCategory(Category category);

    Category updateCategory(Category category, Long id);

    void deleteCategory(Long id);

    Optional<Category> getCategoryById(Long id);

    List<Category> getAllCategories();
}
