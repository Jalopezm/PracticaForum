package com.liceu.PracticaForum.service;

import com.liceu.PracticaForum.form.CategoryForm;
import com.liceu.PracticaForum.model.Category;
import com.liceu.PracticaForum.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    public void newCategory(Category category) {
        categoryRepo.save(category);
    }

    public String getSlug(String title) {
        String slug = "";
        List<Category> slugExists = categoryRepo.findByTitle(title);

        if (slugExists.size() > 0) {
            slug = title.toLowerCase().replace(" ", "-") + "-" + slugExists.size();
        } else {
            slug = title.toLowerCase().replace(" ", "-");
        }
        return slug;
    }

    public Map<String, Object> createCategoryMap(CategoryForm categoryForm, Map<String, Object> categoryMap) {
        categoryMap.put("moderators", new ArrayList<>());
        categoryMap.put("title", categoryForm.getTitle());
        categoryMap.put("description", categoryForm.getDescription());
        return categoryMap;
    }

    public String getColor() {
        Random rand = new Random();
        int r = (int) (rand.nextFloat() * 256);
        int g = (int) (rand.nextFloat() * 256);
        int b = (int) (rand.nextFloat() * 256);
        Color randomColor = new Color(r, g, b);

        return String.valueOf(randomColor);
    }

    public Category getCategoryBySlug(String categorySlug) {
        return categoryRepo.getCategoryBySlug(categorySlug);
    }
}
