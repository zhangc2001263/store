package com.zc.store.controller;

import com.zc.store.entity.Address;
import com.zc.store.service.IAddressService;
import com.zc.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController extends BaseController {

    @Autowired
    IAddressService addressService;

    @RequestMapping("/addnewaddress")
    public JsonResult addNewAddress(Address address, HttpSession session) {
        Integer uidFromSession = getUidFromSession(session);
        String usernameFromSession = getUsernameFromSession(session);

        addressService.addNewAddress(uidFromSession, usernameFromSession, address);

        return new JsonResult(OK);
    }

    @RequestMapping({"/", ""})
    public JsonResult getInfoByUid(HttpSession session) {
        Integer uidFromSession = getUidFromSession(session);
        List<Address> list = addressService.getInfoByUid(uidFromSession);

        return new JsonResult(OK, list);
    }

    @RequestMapping("/findisdefault")
    public JsonResult findIsDefault(HttpSession session) {
        Integer uidFromSession = getUidFromSession(session);
        Address address = addressService.findIsDefault(uidFromSession);

        return new JsonResult(OK, address);
    }

    /**
     * restful风格，可以将请求路径中的{}中的值赋给方法的同名参数，@PathVariable注解是将请求路径中的某个值赋给它跟着的参数，
     * 这里意思是将路径中的aid赋值给参数中的aid,在前端发起请求时携带的参数要和你这里的{}所在的位置一致
     *
     * @param aid
     * @param session
     * @return
     */
    @RequestMapping("{aid}/setdefault")
    public JsonResult setDefault(@PathVariable("aid") Integer aid, HttpSession session) {
        addressService.setDefault(aid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult(OK);
    }

    @RequestMapping("{aid}/delete")
    public JsonResult deleteAddress(@PathVariable("aid") Integer aid, HttpSession session) {
        addressService.deleteAddress(aid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult(OK);
    }
}
