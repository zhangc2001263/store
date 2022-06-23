package com.zc.store.mapper;

import com.zc.store.entity.District;

import java.util.List;

public interface DistrictMapper {

    /**
     * 根据父代号查询区域信息
     *
     * @param parent 父代号
     * @return 某个父区域下的所有区域列表
     */
    List<District> findByParent(String parent);

    /**
     * 根据区域编号找出对应的区域
     * @param code 区域编号
     * @return 区域的名称
     */
    String findNameByCode(String code);
}
