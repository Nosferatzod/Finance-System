package com.example.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Builder
@Entity(name="User")
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User extends BaseModel{
    @NotBlank
    @Setter
    @Column(nullable = true)
    private String name;
    @Setter
    @NotBlank
    @Column(nullable = false)
    private String email;
    @NotBlank
    @Setter
    @Column(nullable = true)
    private String cpf;
    @Setter
    @NotBlank
    @Column(nullable = false)
    private String password;
    @NotEmpty
    @Column(nullable = false)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name="users_roles",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles;
    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getCpf() {return cpf;}
    public String getPassword() {return password;}
    public List<Role> getRoles() {return roles;}
    public void setRoles(@NotBlank List<Role> roles) {this.roles = roles;}
}
