package com.liceu.PracticaForum.service;

import com.liceu.PracticaForum.form.CategoryForm;
import com.liceu.PracticaForum.model.Category;
import com.liceu.PracticaForum.model.User;
import com.liceu.PracticaForum.repo.CategoryRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    UserService userService;

    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    public Map<String, Object> newCategory(CategoryForm categoryForm, HttpServletRequest request) {
        Map<String, Object> categoryMap = new HashMap<>();
        categoryMap = createCategoryMap(categoryForm, categoryMap);

        String color = getColor();
        String slug = getSlug(categoryForm.getTitle());
        categoryMap.put("slug", slug);
        categoryMap.put("color", color);
        Category category = new Category(categoryForm.getTitle(), categoryForm.getDescription(), slug, color);

        User user = (User) userService.getUser(request);
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        category.setUser(newUser);

        categoryRepo.save(category);

        return categoryMap;
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

    public void updateCategory(String categorySlug, CategoryForm categoryForm) {
        categoryRepo.updateCategory(categorySlug, categoryForm.getDescription(), categoryForm.getTitle());
    }

    public Boolean deleteCategory(String categorySlug, User user) {
        Category category = getCategoryBySlug(categorySlug);
        if (user.getRole().equals("admin") || user.getId().equals(category.getUser().getId())) {
            try {
                categoryRepo.deleteById(category.getId());
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

}
