package com.bobooi.mall.data.repository.concrete;

import com.bobooi.mall.data.entity.PdtAddiInf;
import com.bobooi.mall.data.repository.DataRepository;

import java.util.List;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
public interface PdtAddiInfoRepository extends DataRepository<PdtAddiInf,Integer> {
    List<PdtAddiInf> findAllByProductId(Integer productId);
    PdtAddiInf findByProductIdAndProductTypeId(Integer productId, Integer productTypeId);
}
