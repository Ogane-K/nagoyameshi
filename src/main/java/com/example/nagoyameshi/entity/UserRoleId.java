package com.example.nagoyameshi.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor

public class UserRoleId implements Serializable {

    private Integer userId;

    private Integer roleId;

}
