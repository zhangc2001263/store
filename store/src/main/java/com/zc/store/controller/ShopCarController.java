package com.zc.store.controller;

import com.zc.store.mapper.ShopCarMapper;
import com.zc.store.service.IShopCarService;
import com.zc.store.util.JsonResult;
import com.zc.store.vo.ShopCarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/shopcar")
public class ShopCarController extends BaseController {

    @Autowired
    IShopCarService shopCarService;

    @RequestMapping("/addtoshopcar")
    public JsonResult addToShopCar(Integer pid, Integer amount, HttpSession session) {

        shopCarService.addToShopCar(getUidFromSession(session), pid, amount, getUsernameFromSession(session));
        return new JsonResult(OK);
    }

    @RequestMapping({"", "/"})
    public JsonResult getVOBydUid(HttpSession session) {
        List<ShopCarVO> data = shopCarService.getVOByUid(getUidFromSession(session));
        return new JsonResult(OK, data);
    }

    @RequestMapping("{cid}/addnum")
    public JsonResult addNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer data = shopCarService.addNum(cid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult(OK, data);
    }

    @RequestMapping("{cid}/reducenum")
    public JsonResult reduceNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer data = shopCarService.reduceNum(cid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult(OK, data);
    }

    @RequestMapping("/list")
    public JsonResult getVOBydCid(Integer[] cids, HttpSession session) {
        List<ShopCarVO> data = shopCarService.findVOByCids(getUidFromSession(session), cids);
        return new JsonResult(OK, data);
    }

    @RequestMapping("{cid}/delete")
    public JsonResult delete(@PathVariable("cid") Integer cid) {
        shopCarService.deleteGood(cid);
        return new JsonResult(OK);
    }

    @RequestMapping("/deletegoods")
    public JsonResult deleteGoods(Integer[] cids) {
        shopCarService.deleteGoods(cids);
        return new JsonResult(OK);
    }
}
