package com.zc.store.mapper;

import com.zc.store.entity.Favorite;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface FavoriteMapper {

    /**
     * 添加收藏商品
     *
     * @param favorite
     * @return
     */
    Integer insertFavorite(Favorite favorite);

    /**
     * 根据uid找到该用户的所有收藏商品
     * @param uid
     * @return
     */
    List<Favorite> selectFavorites(Integer uid);

    /**
     * 根据fid删除收藏的商品
     * @param fid
     * @return
     */
    Integer deleteFavorites(Integer fid);

}