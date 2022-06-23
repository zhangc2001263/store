package com.zc.store.service;

import com.zc.store.entity.Favorite;

import java.util.List;

public interface IFavoriteService {

    /**
     * 添加收藏商品
     * @param favorite
     * @return
     */
    void insertFavorite(Favorite favorite, Integer uid, String username, Integer id);

    /**
     * 根据uid查找收藏的商品
     * @param uid
     * @return
     */
    List<Favorite> selectFavorites(Integer uid);

    void deleteFavorites(Integer fid);
}
