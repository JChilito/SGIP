package dev.chilito.backend.inventory.application.input;

import java.util.List;

import dev.chilito.backend.inventory.domain.models.Category;

public interface ICategoryInPort {
    Category createCategory(Category category);

    Category updateCategory(Category category, Long id);

    void deleteCategory(Long id);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();
}
