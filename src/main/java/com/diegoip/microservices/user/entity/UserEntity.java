package com.diegoip.microservices.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Data
@Table(name = "tbl_user")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private String id;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "created", updatable = false)
  private LocalDateTime created;

  @Column(name = "modified")
  private LocalDateTime modified;

  @Column(name = "last_login")
  private LocalDateTime lastLogin;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "token")
  private String token;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<UserPhoneEntity> phones;

  public void addPhone(UserPhoneEntity phone) {
    if (phones == null) {
      phones = new ArrayList<>();
    }

    phones.add(phone);
    phone.setUser(this);
  }
}
