package com.example.edo.models;

import com.example.edo.models.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;
    @NotEmpty(message = "Поле ФИО быть заполнено")
//    @Size(min = 2, max = 45, message = "Минимальная длина фио 2 символа")
    @Column(name = "name")
    private String name;
//    @NotEmpty(message = "Поле эл. почта быть заполнено")
    @Email(message = "Введите корректный почтовый адрес")
    @Column(name = "mail", unique = true)
    private String mail;
    @NotEmpty(message = "Поле пароль должно быть заполнено")
//    @Min(value = 8, message = "Пароль должен содержать минимум 8 символов")
    @Column(name = "password")
    private String password;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();
    @Column(name = "number_group")
    private String numberGroup;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isStudent(){
        return roles.contains(Role.ROLE_student);
    }

    public boolean isTeacher(){
        return roles.contains(Role.ROLE_teacher);
    }

    //todo: переделать данную модель в модель доп информации о пользователе, не хранить здесь пароль,
    // при регистрации пользователя отправлять запрос в сервис аутентификации с логином и паролем,
    // а дополнительные данные(номер группы, фио, и тд) хранить здесь
}
