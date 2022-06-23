package com.zc.store.controller;

import com.zc.store.entity.Favorite;
import com.zc.store.service.IFavoriteService;
import com.zc.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("/favorite")
public class FavoriteController extends BaseController{

    @Autowired
    IFavoriteService favoriteService;

    @RequestMapping("/insertfavorite")
    public JsonResult insertFavorite(Favorite favorite, Integer id, HttpSession session) {
        favoriteService.insertFavorite(favorite, getUidFromSession(session), getUsernameFromSession(session), id);
        return new JsonResult(OK);
    }

    @RequestMapping("/findfavorite")
    public JsonResult findFavorite(HttpSession session) {
        List<Favorite> favorites = favoriteService.selectFavorites(getUidFromSession(session));
        return new JsonResult(OK, favorites);
    }

    @RequestMapping("{fid}/deletefavorite")
    public JsonResult deleteFavorite(@PathVariable("fid") Integer fid) {
        favoriteService.deleteFavorites(fid);
        return new JsonResult(OK);
    }
}
