package com.liceu.PracticaForum.model;

import jakarta.persistence.*;


import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String description;
    String slug;
    String color;
    @OneToMany(mappedBy = "category")
    Set<Topic> moderators;

    public Set<Topic> getModerators() {
        return moderators;
    }

    public void setModerators(Set<Topic> moderators) {
        this.moderators = moderators;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Category(String title, String description, String slug, String color) {
        this.title = title;
        this.description = description;
        this.slug = slug;
        this.color = color;
    }

    public Category() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
