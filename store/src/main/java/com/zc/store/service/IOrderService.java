package com.zc.store.service;

import com.zc.store.entity.Order;
import com.zc.store.entity.OrderItem;

import java.util.List;

public interface IOrderService {

    /**
     * 创建订单
     * @param aid 地址id
     * @param cids 购物车id
     * @param uid 用户id
     * @param username 用户名
     * @return order对象
     */
    Order createOrder(Integer aid, Integer[] cids, Integer uid, String username);

//    List<Order> selectUserByOidAndUid(Integer uid);

    List<OrderItem> selectOrderItem(String username);

    OrderItem seleteStatus(Integer id);

    void deleteOrderItem(Integer id);
}
