package com.zc.store.service.impl;

import com.zc.store.entity.District;
import com.zc.store.mapper.DistrictMapper;
import com.zc.store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {

    @Autowired
    DistrictMapper districtMapper;

    @Override
    public List<District> findByParent(String parent) {

        List<District> list = districtMapper.findByParent(parent);

        /**
         * 在进行网络数据传输的时候，将无关紧要的数据设置为null，可以节省流量，提升效率
         * 数据库表中有4个字段，由于查询结果只要目标区域列表名字和代号，parent和id为无效数据，应设置为null
         */
        //将查询到的结果中的id和parent都设置为null
        for (District d : list) {
            d.setId(null);
            d.setParent(null);
        }
        return list;
    }

    @Override
    public String findNameByCode(String code) {

        String nameByCode = districtMapper.findNameByCode(code);

        return nameByCode;
    }
}
