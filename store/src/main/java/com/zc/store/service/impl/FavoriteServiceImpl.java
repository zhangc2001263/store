package com.zc.store.service.impl;

import com.zc.store.entity.Favorite;
import com.zc.store.entity.Product;
import com.zc.store.mapper.FavoriteMapper;
import com.zc.store.service.IFavoriteService;
import com.zc.store.service.IProductService;
import com.zc.store.service.ex.DeleteException;
import com.zc.store.service.ex.FavoriteNotFoundException;
import com.zc.store.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FavoriteServiceImpl implements IFavoriteService {

    @Autowired
    FavoriteMapper favoriteMapper;

    @Autowired
    IProductService productService;

    @Override
    public void insertFavorite(Favorite favorite, Integer uid, String username, Integer id) {
        Product product = productService.findById(id);
        favorite.setPid(product.getId());
        favorite.setTitle(product.getTitle());
        favorite.setPrice(product.getPrice());
        favorite.setImage(product.getImage());
        favorite.setUid(uid);
        favorite.setCreateUser(username);
        favorite.setCreateTime(new Date());
        Integer integer = favoriteMapper.insertFavorite(favorite);
        if (integer != 1) {
            throw new InsertException("添加收藏时发生未知异常");
        }
    }

    @Override
    public List<Favorite> selectFavorites(Integer uid) {
        List<Favorite> favorites = favoriteMapper.selectFavorites(uid);
        if (favorites == null) {
            throw new FavoriteNotFoundException("没有找到收藏的商品");
        }
        return favorites;
    }

    @Override
    public void deleteFavorites(Integer fid) {
        Integer integer = favoriteMapper.deleteFavorites(fid);
        if (integer != 1) {
            throw new DeleteException("删除收藏时发生未知异常");
        }
    }
}
