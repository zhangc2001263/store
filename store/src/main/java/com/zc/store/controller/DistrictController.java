package com.zc.store.controller;

import com.zc.store.entity.District;
import com.zc.store.service.IDistrictService;
import com.zc.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.soap.Addressing;
import java.util.List;

@RestController
@RequestMapping("/district")
public class DistrictController extends BaseController {

    @Autowired
    IDistrictService districtService;

    @RequestMapping({"/", ""})
    public JsonResult findByParent(String parent) {
        List<District> list = districtService.findByParent(parent);
        return new JsonResult(OK, list);
    }

    public JsonResult findNameByCode(String code) {
        String nameByCode = districtService.findNameByCode(code);
        return new JsonResult(OK,nameByCode);
    }
}
