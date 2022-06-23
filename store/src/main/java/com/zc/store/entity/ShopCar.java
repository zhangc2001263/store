package com.zc.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class ShopCar extends BaseEntity {
    //购物车的id
    private Integer cid;
    //用户的id
    private Integer uid;
    //商品的id
    private Integer pid;
    //商品在加入购物车时的价格
    private long price;
    //商品的数量
    private Integer num;
}
