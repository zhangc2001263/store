package com.zc.store.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * sql语句实现多表联查后，结果不只属于一个实体类对象，此时结果集类型不再是任何一个单一的实体类型
 * 此时就可以重新创建一个新的类vo类，value object，值对象，这个类中你可以自定义多表联查后的结果的属性
 * 这个类仅仅是为了多表联查使用，所以不写在实体类中
 */

/**
 * 购物车数据的vo类
 */
@Data
@EqualsAndHashCode
@ToString
public class ShopCarVO implements Serializable {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
    private String title;
    private Long realPrice;
    private String image;
}
