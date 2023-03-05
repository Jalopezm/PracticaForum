package com.liceu.PracticaForum.repo;

import com.liceu.PracticaForum.model.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,Long> {
    List<Category> findByTitle(String tile);

    Category getCategoryBySlug(String categorySlug);
    @Modifying
    @Transactional
    @Query("UPDATE Category c SET c.description=:description,c.title=:title WHERE c.slug=:slug")
    void updateCategory(String slug, String description, String title);
}
