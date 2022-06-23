package com.zc.store.service.impl;

import com.zc.store.entity.Address;
import com.zc.store.mapper.AdderssMapper;
import com.zc.store.service.IAddressService;
import com.zc.store.service.IDistrictService;
import com.zc.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    AdderssMapper adderssMapper;

    @Autowired
    IDistrictService districtService;

    //yaml文件中自定义配置user.address.max-count表示地址的最大信息条数
    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        Integer integer = adderssMapper.countByUid(uid);
        if (integer > maxCount) {
            throw new AddressCountLimitException("插入信息条数不得超出20条");
        }

        //获取省市区的名称
        String proviceName = districtService.findNameByCode(address.getProvinceCode());
        String cityName = districtService.findNameByCode(address.getCityCode());
        String areaName = districtService.findNameByCode(address.getAreaCode());
        //补全address信息
        address.setProvinceName(proviceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        address.setUid(uid);
        //上面查出来的count为0说明当前用户没有收货地址，此时正在添加的就是第一个收货地址，就把这个地址当做默认地址
        int isDefault = integer == 0 ? 1 : 0;
        address.setIsDefault(isDefault);
        //补全address中的信息
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());
        Integer insert = adderssMapper.insert(address);
        if (insert != 1) {
            throw new InsertException("插入收货地址时发生异常");
        }
    }

    @Override
    public List<Address> getInfoByUid(Integer uid) {

        List<Address> infoByUid = adderssMapper.findInfoByUid(uid);
        //前端页面只需要表中的某些数据，不需要的数据可以设置为空。提升效率
        for (Address address : infoByUid) {
            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
        }
        return infoByUid;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address address = adderssMapper.findByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException("收货地址不存在");
        }
        Integer integer = adderssMapper.updateNonDefault(uid);
        if (integer < 1) {
            throw new UpdateException("更新时产生异常");
        }
        Integer rows = adderssMapper.updateDefaultByAid(aid, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新时产生异常");
        }
    }

    @Override
    public void deleteAddress(Integer aid, Integer uid, String username) {
        //先查询这个地址存不存在
        Address byAid = adderssMapper.findByAid(aid);
        if (byAid == null) {
            throw new AddressNotFoundException("地址不存在");
        }

        Integer result = adderssMapper.deleteAddressByAid(aid);
        if (result != 1) {
            throw new DeleteException("删除时产生未知异常");
        }

        //查询当前用户的地址条数，判断删除的地址是不是最后一条
        Integer count = adderssMapper.countByUid(uid);
        if (count == 0) {
            //如果查出数据为0，说明刚刚删除的就是最后一条数据，此时也不需要重新找一个地址设置为默认，直接退出就行
            return;
        }

        //如果删除的地址不是默认值，删除之后就不需要进行其他操作
        if (byAid.getIsDefault() == 0) {
            return;
        }

        //走到这里说明删除的是默认地址并且删除后数据库中还有地址，此时就需要重新设置一个默认的收货地址，规则是最后修改的地址设置为默认地址
        //先找到最后修改的那条地址信息
        Address address = adderssMapper.findLastModified(uid);

        result = adderssMapper.updateDefaultByAid(address.getAid(), username, new Date());
        if (result != 1) {
            throw new UpdateException("更新时发生未知异常");
        }
    }

    @Override
    public Address getAddressByAid(Integer aid, Integer uid) {
        Address address = adderssMapper.findByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException("未找到该地址");
        }
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }
        //将不需要的字段数据设置为空
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);
        return address;
    }

    @Override
    public Address findIsDefault(Integer uid) {
        Address isDefault = adderssMapper.findIsDefault(uid);
        if (isDefault == null) {
            throw new AddressNotFoundException("未找到改地址");
        }
        return isDefault;
    }


}
