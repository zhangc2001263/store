package com.zc.store.service;

import com.zc.store.vo.ShopCarVO;

import java.util.List;

public interface IShopCarService {

    /**
     * 添加商品到购物车中
     *
     * @param uid      用户的id(userid)
     * @param pid      商品的id(productid)
     * @param amount   商品的数量
     * @param username 用户名，作为修改者使用
     */
    void addToShopCar(Integer uid, Integer pid, Integer amount, String username);


    /**
     * 查询用户的购物车信息
     *
     * @param uid
     * @return
     */
    List<ShopCarVO> getVOByUid(Integer uid);

    /**
     * 增加购物车中商品数量
     *
     * @param cid      购物车id
     * @param uid      用户id
     * @param username 用户名
     * @return 直接把最终的数量返回
     */
    Integer addNum(Integer cid, Integer uid, String username);

    /**
     * 减少购物车中商品数量
     *
     * @param cid      购物车id
     * @param uid      用户id
     * @param username 用户名
     * @return 直接把最终的数量返回
     */
    Integer reduceNum(Integer cid, Integer uid, String username);

    List<ShopCarVO> findVOByCids(Integer uid, Integer[] cids);

    void deleteGood(Integer cid);

    void deleteGoods(Integer[] cids);
}