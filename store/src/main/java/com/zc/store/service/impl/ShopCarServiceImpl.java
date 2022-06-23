package com.zc.store.service.impl;

import com.zc.store.entity.Product;
import com.zc.store.entity.ShopCar;
import com.zc.store.mapper.ProductMapper;
import com.zc.store.mapper.ShopCarMapper;
import com.zc.store.service.IShopCarService;
import com.zc.store.service.ex.AccessDeniedException;
import com.zc.store.service.ex.CartNotFoundException;
import com.zc.store.service.ex.DeleteException;
import com.zc.store.service.ex.InsertException;
import com.zc.store.vo.ShopCarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


@Service
public class ShopCarServiceImpl implements IShopCarService {

    @Autowired
    ShopCarMapper shopCarMapper;

    /**
     * 购物车的业务层需要依赖商品的持久层
     */
    @Autowired
    ProductMapper productMapper;

    @Override
    public void addToShopCar(Integer uid, Integer pid, Integer amount, String username) {
        ShopCar result = shopCarMapper.findByUidAndPid(uid, pid);
        Date now = new Date();
        if (result == null) {
            ShopCar shopCar = new ShopCar();
            // 封装数据：uid,pid,amount
            shopCar.setUid(uid);
            shopCar.setPid(pid);
            shopCar.setNum(amount);
            // 调用productMapper.findById(pid)查询商品数据，得到商品价格
            Product product = productMapper.findById(pid);
            // 封装数据：price
            shopCar.setPrice(product.getPrice());
            // 封装数据：4个日志
            shopCar.setCreatedUser(username);
            shopCar.setCreatedTime(now);
            shopCar.setModifiedUser(username);
            shopCar.setModifiedTime(now);
            Integer integer = shopCarMapper.insertToShopCar(shopCar);
            if (integer != 1) {
                throw new InsertException("插入数据时产生未知异常");
            }
        } else {
            // 否：表示该用户的购物车中已有该商品
            // 从查询结果中获取购物车数据的id
            Integer cid = result.getCid();
            // 从查询结果中取出原数量，与参数amount相加，得到新的数量
            Integer num = result.getNum() + amount;
            // 执行更新数量
            Integer rows = shopCarMapper.updateNumByCid(cid, num, username, now);
            if (rows != 1) {
                throw new InsertException("修改商品数量时出现未知错误，请联系系统管理员");
            }

        }
    }

    @Override
    public List<ShopCarVO> getVOByUid(Integer uid) {
        List<ShopCarVO> vo = shopCarMapper.findVOByUid(uid);
        return vo;
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        //先查找当前购物车数据是否存在
        ShopCar result = shopCarMapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("购物车数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("数据非法访问");
        }
        //给num加一
        Integer num = result.getNum() + 1;
        //执行更新语句更新num
        Integer integer = shopCarMapper.updateNumByCid(cid, num, username, new Date());
        return num;
    }

    @Override
    public Integer reduceNum(Integer cid, Integer uid, String username) {
        //先查找当前购物车数据是否存在
        ShopCar result = shopCarMapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("购物车数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("数据非法访问");
        }
        Integer num = result.getNum();
        //给num加一
        if (result.getNum() > 1){
            num = result.getNum() - 1;
            //执行更新语句更新num
            Integer integer = shopCarMapper.updateNumByCid(cid, num, username, new Date());
        }

        return num;
    }

    @Override
    public List<ShopCarVO> findVOByCids(Integer uid, Integer[] cids) {
        List<ShopCarVO> list = shopCarMapper.findVOByCid(cids);
        // 迭代list
        Iterator<ShopCarVO> it = list.iterator();
        while (it.hasNext()) {
            //取出迭代器中的每个list
            ShopCarVO shopCarVO = it.next();
            if (!shopCarVO.getUid().equals(uid)) {
                //如果数据归属错误，就删除这个数据
                list.remove(shopCarVO);
            }
        }
        return list;
    }

    @Override
    public void deleteGood(Integer cid) {
        Integer integer = shopCarMapper.deleteGood(cid);
        if (integer < 1) {
            throw new DeleteException("删除时出现未知异常");
        }
    }

    @Override
    public void deleteGoods(Integer[] cids) {
        Integer result = shopCarMapper.deleteGoods(cids);
        if (result < 1) {
            throw new DeleteException("删除时出现未知异常");
        }
    }
}
