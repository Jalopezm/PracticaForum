package com.liceu.PracticaForum.controller;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.liceu.PracticaForum.form.CategoryForm;
import com.liceu.PracticaForum.model.Category;
import com.liceu.PracticaForum.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CategoriesController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    @CrossOrigin(origins = "http://localhost:8080")
    public List<Category> categories(){
        return categoryService.getAllCategories();
    }


    @PostMapping("/categories")
    @CrossOrigin(origins = "http://localhost:8080")
    public Map<String,Object> newCategory(@RequestBody CategoryForm categoryForm){
        Map<String, Object> categoryMap = new HashMap<>();
        categoryMap = categoryService.createCategoryMap(categoryForm,categoryMap);

        String color = categoryService.getColor();
        String slug = categoryService.getSlug(categoryForm.getTitle());
        categoryMap.put("slug", slug);
        categoryMap.put("color", color);
        Category category = new Category(categoryForm.getTitle(), categoryForm.getDescription(),slug,color);
        categoryService.newCategory(category);
        return categoryMap;
    }

    @GetMapping("/categories/{categorySlug}")
    @CrossOrigin(origins = "http://localhost:8080")
    public Category categorySlug(@PathVariable String categorySlug){
        Category category = categoryService.getCategoryBySlug(categorySlug);
        return category;
    }
}
