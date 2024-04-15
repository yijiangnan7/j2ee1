package com.example.demo2.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author 王帅
 * @since 2023-08-03
 */
@Table("tb_good")
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
public class Good extends Model<Good> {

    @Id(keyType = KeyType.Auto)
    private Integer id;
    private String name;
    private Double price;

}