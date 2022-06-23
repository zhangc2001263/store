package com.zc.store.service.impl;

import com.zc.store.entity.Address;
import com.zc.store.entity.Order;
import com.zc.store.entity.OrderItem;
import com.zc.store.mapper.AdderssMapper;
import com.zc.store.mapper.OrderMapper;
import com.zc.store.mapper.ShopCarMapper;
import com.zc.store.service.IAddressService;
import com.zc.store.service.IOrderService;
import com.zc.store.service.IShopCarService;
import com.zc.store.service.ex.AccessDeniedException;
import com.zc.store.service.ex.DeleteException;
import com.zc.store.service.ex.InsertException;
import com.zc.store.service.ex.OrderNotFoundException;
import com.zc.store.vo.ShopCarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private IShopCarService shopCarService;

    @Override
    public Order createOrder(Integer aid, Integer[] cids, Integer uid, String username) {
        // 创建当前时间对象
        Date now = new Date();

        // 根据cids查询所勾选的购物车列表中的数据
        List<ShopCarVO> shopCarVOS = shopCarService.findVOByCids(uid, cids);

        // 计算这些商品的总价
        long totalPrice = 0;
        for (ShopCarVO cart : shopCarVOS) {
            totalPrice += cart.getRealPrice() * cart.getNum();
        }

        // 创建订单数据对象
        Order order = new Order();
        // 补全数据：uid
        order.setUid(uid);
        // 查询收货地址数据
        Address address = addressService.getAddressByAid(aid, uid);
        // 补全数据：收货地址相关的6项
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        // 补全数据：totalPrice
        order.setTotalPrice(totalPrice);
        // 补全数据：status
        order.setStatus(0);
        // 补全数据：下单时间
        order.setOrderTime(now);
        // 补全数据：日志
        order.setCreatedUser(username);
        order.setCreatedTime(now);
        order.setModifiedUser(username);
        order.setModifiedTime(now);
        // 插入订单数据
        Integer rows1 = orderMapper.insertOrder(order);
        if (rows1 != 1) {
            throw new InsertException("插入订单数据时出现未知错误，请联系系统管理员");
        }

        // 遍历ShopCar，循环插入订单商品数据
        for (ShopCarVO cart : shopCarVOS) {
            // 创建订单商品数据
            OrderItem item = new OrderItem();
            // 补全数据：setOid(order.getOid())
            item.setOid(order.getOid());
            // 补全数据：pid, title, image, price, num
            item.setPid(cart.getPid());
            item.setTitle(cart.getTitle());
            item.setImage(cart.getImage());
            item.setPrice(cart.getRealPrice());
            item.setNum(cart.getNum());
            // 补全数据：4项日志
            item.setCreatedUser(username);
            item.setCreatedTime(now);
            item.setModifiedUser(username);
            item.setModifiedTime(now);
            // 插入订单商品数据
            Integer rows2 = orderMapper.insertOrderItem(item);
            if (rows2 != 1) {
                throw new InsertException("插入订单商品数据时出现未知错误，请联系系统管理员");
            }
        }

        // 返回
        return order;
    }

    @Override
    public List<OrderItem> selectOrderItem(String username) {
        List<OrderItem> item = orderMapper.selectOrderItem(username);
        if (item == null) {
            throw new OrderNotFoundException("未找到订单信息");
        }
        return item;
    }

    @Override
    public OrderItem seleteStatus(Integer id) {
        OrderItem order = orderMapper.seleteStatus(id);

        return order;
    }

    @Override
    public void deleteOrderItem(Integer id) {
        Integer integer = orderMapper.deleteOrderItem(id);
        if (integer != 1) {
            throw new DeleteException("删除订单商品失败");
        }
    }

//    @Override
//    public List<Order> selectUserByOidAndUid(Integer uid) {
//        List<Order> orders = orderMapper.selectUserByOidAndUid(uid);
//
//        return orders;
}

