package com.zc.store.mapper;

import com.zc.store.entity.Order;
import com.zc.store.entity.OrderItem;

import java.util.List;

public interface OrderMapper {

    /**
     * 插入订单信息
     * @param order order对象
     * @return 受影响的条数
     */
    Integer insertOrder(Order order);

    /**
     * 插入订单项数据
     * @param orderItem 订单项对象
     * @return 受影响的条数
     */
    Integer insertOrderItem(OrderItem orderItem);

    /**
     * 根据用户名查询订单中的商品
     * @param username
     * @return
     */
    List<OrderItem> selectOrderItem(String username);
//    List<Order> selectUserByOidAndUid(Integer uid);

    /**
     * 根据id查询订单中的商品信息，用于前端展示当前商品的状态
     * @param id
     * @return
     */
    OrderItem seleteStatus(Integer id);

    OrderItem selectOrderById(Integer id);

    /**
     * 点击确认收货之后删除对应的订单商品
     * @param id
     * @return
     */
    Integer deleteOrderItem(Integer id);
}
