package com.example.edo.Models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_student, ROLE_teacher;

    @Override
    public String getAuthority() {
        return name();
    }
}
