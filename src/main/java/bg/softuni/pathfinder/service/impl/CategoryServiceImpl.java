package bg.softuni.pathfinder.service.impl;

import bg.softuni.pathfinder.model.entity.CategoryEntity;
import bg.softuni.pathfinder.model.entity.enums.CategoryNameEnum;
import bg.softuni.pathfinder.repository.CategoryRepository;
import bg.softuni.pathfinder.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryEntity findByName(CategoryNameEnum categoryName) {

        return categoryRepository.findByName(categoryName)
                .orElse(null);
    }
}
