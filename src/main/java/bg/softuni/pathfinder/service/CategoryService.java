package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.entity.CategoryEntity;
import bg.softuni.pathfinder.model.entity.enums.CategoryNameEnum;

public interface CategoryService {
    CategoryEntity findByName(CategoryNameEnum categoryName);
}
