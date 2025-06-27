package com.example.nagoyameshi.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class UserRoleId implements Serializable{
    private Integer userId;
    private Integer roleId;

}
