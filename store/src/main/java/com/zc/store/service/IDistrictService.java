package com.zc.store.service;

import com.zc.store.entity.District;

import java.util.List;

/**
 * 处理省/市/区数据的业务层接口
 */
public interface IDistrictService {

    /**
     * 根据父区域代号查询区域的信息
     * @param parent 父区域代号
     * @return 区域列表
     */
    List<District> findByParent(String parent);

    /**
     * 根据区域代号查找对应区域的名称
     * @param code 区域代号
     * @return 区域名称
     */
    String findNameByCode(String code);
}
