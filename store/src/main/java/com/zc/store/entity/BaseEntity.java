package com.zc.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 作为实体类的基类，有多张表中有重复字段，每次都创建一个类冗余了，可以将多章表中的重复字段创建一个基类
 */
@Data
@EqualsAndHashCode
@ToString
public class BaseEntity implements Serializable {

    private String createdUser;
    private Date createdTime;
    private String modifiedUser;
    private Date modifiedTime;

}
