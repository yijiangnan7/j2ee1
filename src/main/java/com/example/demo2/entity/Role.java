package com.example.demo2.entity;

import com.mybatisflex.annotation.*;
import lombok.Data;

/**
 * @author 王帅
 * @since 2023-07-14
 */
@Data
@Table("tb_role")
public class Role {

    @ColumnAlias("role_id")
    @Id(keyType = KeyType.Auto)
    private Long id;
    private String roleKey;
    private String roleName;
    @Column(isLarge = true)
    private String description;

}