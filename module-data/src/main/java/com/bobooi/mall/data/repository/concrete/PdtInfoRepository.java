package com.bobooi.mall.data.repository.concrete;

import com.bobooi.mall.data.entity.PdtInf;
import com.bobooi.mall.data.repository.DataRepository;

import java.util.List;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
public interface PdtInfoRepository extends DataRepository<PdtInf,Integer> {
    /**
     * 根据商品分类id获取所有商品
     *
     * @param categoryId 商品分类id
     * @return
     */
    List<PdtInf> findAllByCategoryId(Integer categoryId);

    /**
     * 根据商品id获取商品信息
     *
     * @param productId 商品id
     * @return
     */
    PdtInf findByProductId(Integer productId);
}
