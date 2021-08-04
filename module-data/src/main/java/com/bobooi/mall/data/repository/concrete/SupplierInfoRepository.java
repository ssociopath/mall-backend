package com.bobooi.mall.data.repository.concrete;

import com.bobooi.mall.data.entity.SupplierInfo;
import com.bobooi.mall.data.repository.DataRepository;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
public interface SupplierInfoRepository extends DataRepository<SupplierInfo,Integer> {
    /**
     * 根据供应商id查找供应商信息
     *
     * @param supplierId 供应商id
     * @return
     */
    SupplierInfo findBySupplierId(Integer supplierId);
}
