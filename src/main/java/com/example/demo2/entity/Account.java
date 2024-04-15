package com.example.demo2.entity;
import com.example.demo2.listener.AccountCryptListener;
import com.example.demo2.listener.AccountDictListener;
import com.example.demo2.listener.AccountPermissionListener;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.core.annotation.Order;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Table(value = "tb_account", dataSource = "ds1",
        onSet = {
                AccountPermissionListener.class,
                AccountCryptListener.class,
                AccountDictListener.class
        })
public class Account implements Serializable {

    private Integer gender;

    @Id(keyType = KeyType.Auto)
    private Long id;
    private String password;
    private String userName;
    @Column(ignore = true)
    private String genderName;
    private Integer age;
    private Date birthday;
    private Integer deptId;

    private List<Order> orderList;

    @Tolerate
    public Account() {
        // SuppressWarnings
    }

}