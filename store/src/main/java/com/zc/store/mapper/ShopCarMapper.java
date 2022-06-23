package com.zc.store.mapper;

import com.zc.store.entity.ShopCar;
import com.zc.store.vo.ShopCarVO;

import java.util.Date;
import java.util.List;

public interface ShopCarMapper {

    /**
     * 往购物车中添加商品
     *
     * @param shopCar 购物车对象
     * @return 受影响的行数
     */
    Integer insertToShopCar(ShopCar shopCar);

    /**
     * 修改购物车数据中商品的数量
     *
     * @param cid          购物车数据的id
     * @param num          新的数量
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateNumByCid(
            Integer cid,
            Integer num,
            String modifiedUser,
            Date modifiedTime);

    /**
     * 根据用户id和商品id查询购物车中的数据
     *
     * @param uid 用户id
     * @param pid 商品id
     * @return 购物车对象
     */
    ShopCar findByUidAndPid(Integer uid, Integer pid);

    /**
     * 查询用户的购物车信息，显示出购物车商品
     * @param uid 用户的id
     * @return 类型为ShopCarVO的list集合
     */
    List<ShopCarVO> findVOByUid(Integer uid);

    /**
     * 根据cid查询这条购物车信息是否存在
     * @param cid cid
     * @return 一个购物车对象，购物车信息都封装在一个购物车对象中
     */
    ShopCar findByCid(Integer cid);

    /**
     * 查询勾选的购物车信息
     * @param cids 购物车的id集合
     * @return 类型为ShopCarVO的list集合
     */
    List<ShopCarVO> findVOByCid(Integer[] cids);

    /**
     * 删除购物车中的商品
     * @param cid
     * @return
     */
    Integer deleteGood(Integer cid);

    /**
     * 批量删除购物车
     * @param cids
     * @return
     */
    Integer deleteGoods(Integer[] cids);
}
