package com.bobooi.mall.data.repository.concrete.customer;

import com.bobooi.mall.data.entity.customer.CsmLogin;
import com.bobooi.mall.data.repository.DataRepository;

/**
 * @author bobo
 * @date 2021/3/31
    CsmLogin find
 */

public interface CsmLoginRepository extends DataRepository<CsmLogin, Integer> {
    /**
     * 通过帐号和密码查找用户是否存在
     * @param loginName 帐号
     * @param password 密码
     * @return 如果存在，返回user，否则返回null
     */
    CsmLogin findCsmLoginByLoginNameAndPassword(String loginName, String password);

    /**
     * 通过帐号和密码查找用户是否存在
     * @param loginName 帐号
     * @return 如果存在，返回user，否则返回null
     */
    CsmLogin findCsmLoginByLoginName(String loginName);
}
