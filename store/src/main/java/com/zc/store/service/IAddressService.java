package com.zc.store.service;

import com.zc.store.entity.Address;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * 收货地址业务层接口
 */
public interface IAddressService {

    /**
     * 新增收货地址
     *
     * @param uid      用户的id
     * @param username 用户名，作为修改者
     * @param address  地址信息
     */
    void addNewAddress(Integer uid, String username, Address address);

    /**
     * 根据用户id获取用户的所有收货地址
     *
     * @param uid
     * @return
     */
    List<Address> getInfoByUid(Integer uid);

    /**
     * 修改某个用户的某条收货地址为默认收货地址
     * @param aid 地址id
     * @param uid 用户id
     * @param username 修改者名字
     */
    void setDefault(Integer aid, Integer uid, String username);

    /**
     * 删除用户收货地址
     * @param aid
     * @param uid
     * @param username
     */
    void deleteAddress(Integer aid, Integer uid, String username);

    /**
     * 根据地址的id获取对应的地址信息，用来做订单页面的发货地址
     * @param aid 地址的id
     * @return address对象
     */
    Address getAddressByAid(Integer aid, Integer uid);

    Address findIsDefault(Integer uid);
}
