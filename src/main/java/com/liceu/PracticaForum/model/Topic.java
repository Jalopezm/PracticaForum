package com.liceu.PracticaForum.model;

import jakarta.persistence.*;

import javax.annotation.Nullable;
import java.util.Set;

@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String content;

    @ManyToOne
    @Nullable
    @JoinColumn(name = "category_slug")
    Category category;

    @Nullable
    public Category getCategory() {
        return category;
    }

    public void setCategory(@Nullable Category category) {
        this.category = category;
    }

    public Topic(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Topic() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Topic(String title, String category, String content) {
    }
}
