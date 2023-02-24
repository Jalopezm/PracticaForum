package com.liceu.PracticaForum.repo;

import com.liceu.PracticaForum.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,Long> {
    List<Category> findByTitle(String tile);

    Category getCategoryBySlug(String categorySlug);
}
