package com.zc.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
public class Favorite {

    private Integer fid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private String title;
    private String image;
    private String createUser;
    private Date createTime;
}
