package com.zc.store.controller;

import com.zc.store.entity.Order;
import com.zc.store.entity.OrderItem;
import com.zc.store.service.IOrderService;
import com.zc.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController{

    @Autowired
    IOrderService orderService;
    @RequestMapping("/createorder")
    public JsonResult creatOrder(Integer aid, Integer[] cids, HttpSession session) {
        Order order = orderService.createOrder(aid, cids, getUidFromSession(session), getUsernameFromSession(session));

        return new JsonResult(OK, order);
    }

//    @RequestMapping({"","/"})
//    public JsonResult seleteUser(HttpSession session) {
//        List<Order> order = orderService.selectUserByOidAndUid(getUidFromSession(session));
//
//        return new JsonResult(OK, order);
//    }

    @RequestMapping({"","/"})
    public JsonResult selectOrder(HttpSession session) {
        List<OrderItem> orderItems = orderService.selectOrderItem(getUsernameFromSession(session));
        return new JsonResult(OK, orderItems);
    }

    @RequestMapping("/status")
    public JsonResult status(Integer id) {
        OrderItem order = orderService.seleteStatus(id);
        return new JsonResult(OK, order);
    }

    @RequestMapping("{id}/deleteorderitem")
    public JsonResult deleteOrderItem(@PathVariable("id") Integer id) {
        orderService.deleteOrderItem(id);
        return new JsonResult(OK);
    }
}
