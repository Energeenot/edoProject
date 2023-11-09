package com.example.edo.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
public class Post {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    private String title;
    private String isChanged;

    public String getIsChanged() {
        return isChanged;
    }

    public void setIsChanged(String isChanged) {
        this.isChanged = isChanged;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Post(String title, String isChanged) {
        this.title = title;
        this.isChanged = isChanged;
    }

    public Post() {
    }

    //    public boolean isChanged() {
//        return isChanged;
//    }
//
//    public void setChanged(boolean changed) {
//        isChanged = changed;
//    }
}