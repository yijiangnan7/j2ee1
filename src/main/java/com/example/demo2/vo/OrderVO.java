package com.example.demo2.vo;

import com.example.demo2.entity.Good;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.RelationManyToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 王帅
 * @since 2023-08-03
 */
@Data
public class OrderVO {

    @Column(value = "order_id")
    private Long id;
    private LocalDateTime createTime;
    @RelationManyToMany(
            joinTable = "tb_order_good",
            joinSelfColumn = "order_id",
            joinTargetColumn = "good_id"
    )
    private List<Good> goodList;

}