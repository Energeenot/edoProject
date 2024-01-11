package com.example.edo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String isChanged;

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
