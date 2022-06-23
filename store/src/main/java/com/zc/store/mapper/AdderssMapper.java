package com.zc.store.mapper;

import com.zc.store.entity.Address;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * 收货地址持久层接口
 */
public interface AdderssMapper {

    /**
     * 插入收货地址
     *
     * @param address 地址信息都在address对象中
     * @return 受影响的行数
     */
    Integer insert(Address address);

    /**
     * 根据用户的uid统计收货地址的数量
     *
     * @param uid
     * @return 用户的收货地址总数
     */
    Integer countByUid(Integer uid);

    /**
     * 根据用户的id查询该用户的收货地址
     *
     * @param uid id
     * @return 收货地址
     */
    List<Address> findInfoByUid(Integer uid);

    /**
     * 根据aid查询收货地址数据
     *
     * @param aid 收货地址的id
     * @return
     */
    Address findByAid(Integer aid);

    /**
     * 将用户的的所有收货地址的默认值都改为0，都设置为非默认
     *
     * @param uid 根据uid查找用户
     * @return 受影响的行数
     */
    Integer updateNonDefault(Integer uid);

    /**
     * 将目标地址的默认值修改为1，为默认
     *
     * @param aid          目标地址的aid
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateDefaultByAid(Integer aid, String modifiedUser, Date modifiedTime);

    /**
     * 根据aid删除收货地址
     *
     * @param aid
     * @return
     */
    Integer deleteAddressByAid(Integer aid);

    /**
     * 根据uid查出当前用户最后一次修改的地址是哪个
     * @param uid
     * @return 收货地址
     */
    Address findLastModified(Integer uid);

    /**
     * 查找用户收货地址中的默认地址
     * @param uid
     * @return
     */
    Address findIsDefault(Integer uid);
}
