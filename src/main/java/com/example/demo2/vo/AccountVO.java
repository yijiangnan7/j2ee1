package com.example.demo2.vo;

import com.example.demo2.entity.Role;
import com.mybatisflex.annotation.RelationManyToMany;
import com.mybatisflex.annotation.RelationOneToMany;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 * @since 2023-07-09
 */
@Data
@SuppressWarnings("all")
public class AccountVO {

    private Long id;
    private String userName;
    private Integer age;
    private Integer gender;
    private Date birthday;
    //    private Boolean isAdult;
    @RelationManyToMany(
            selfField = "id",
            joinTable = "tb_account_role",
            joinSelfColumn = "account_id",
            joinTargetColumn = "role_id"
    )
    private List<Role> roleList;
    @RelationOneToMany(
            selfField = "id",
            targetField = "id",
            targetTable = "tb_order",
            joinTable = "tb_account_order",
            joinSelfColumn = "account_id",
            joinTargetColumn = "order_id"
    )
    private List<OrderVO> orderList;

}