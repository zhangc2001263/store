package com.zc.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 订单项实体类
 */
@Data
@ToString
@EqualsAndHashCode
public class OrderItem extends BaseEntity{
    private Integer id;
    private Integer oid;
    private Integer pid;
    private String title;
    private String image;
    private Long price;
    private Integer num;

}
