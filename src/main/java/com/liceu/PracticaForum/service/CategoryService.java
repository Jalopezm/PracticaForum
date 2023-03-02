package com.liceu.PracticaForum.service;

import com.liceu.PracticaForum.form.CategoryForm;
import com.liceu.PracticaForum.model.Category;
import com.liceu.PracticaForum.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;

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
        return "hsl(" + r + ", 50%, 50%)";
    }

    public Category getCategoryBySlug(String categorySlug) {
        return categoryRepo.getCategoryBySlug(categorySlug);
    }

    public Object createCategoryMapPermisions() {
        List<Category> categories = categoryRepo.findAll();
        Map<String, Object> categoryMap = new HashMap<>();
        for (Category category : categories) {
            categoryMap.put(category.getSlug(), new String[]{
                    "categories_topics:write",
                    "categories_topics:delete",
                    "categories_replies:write",
                    "categories_replies:delete"
            });
        }
        return categoryMap;
    }
}
