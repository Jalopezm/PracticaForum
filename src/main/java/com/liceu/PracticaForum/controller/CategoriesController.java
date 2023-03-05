package com.liceu.PracticaForum.controller;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.liceu.PracticaForum.form.CategoryForm;
import com.liceu.PracticaForum.model.Category;
import com.liceu.PracticaForum.model.User;
import com.liceu.PracticaForum.service.CategoryService;
import com.liceu.PracticaForum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    @Autowired
    UserService userService;

    @GetMapping("/categories")
    @CrossOrigin(origins = "http://localhost:8080")
    public List<Category> categories(){
        return categoryService.getAllCategories();
    }


    @PostMapping("/categories")
    @CrossOrigin(origins = "http://localhost:8080")
    public Map<String,Object> newCategory(@RequestBody CategoryForm categoryForm, HttpServletRequest request){

        return categoryService.newCategory(categoryForm,request);
    }

    @GetMapping("/categories/{categorySlug}")
    @CrossOrigin(origins = "http://localhost:8080")
    public Category categorySlug(@PathVariable String categorySlug){
        return categoryService.getCategoryBySlug(categorySlug);
    }
    @PutMapping("/categories/{categorySlug}")
    @CrossOrigin(origins = "http://localhost:8080")
    public void categoryUpdate(@PathVariable String categorySlug,@RequestBody CategoryForm categoryForm){
        categoryService.updateCategory(categorySlug,categoryForm);
    }
    @DeleteMapping("/categories/{categorySlug}")
    @CrossOrigin(origins = "http://localhost:8080")
    public Boolean deleteCategory(@PathVariable String categorySlug, HttpServletRequest request){
        return categoryService.deleteCategory(categorySlug, (User) userService.getUser(request));
    }
}
