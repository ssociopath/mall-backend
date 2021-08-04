package com.bobooi.mall.data.repository.concrete;

import com.bobooi.mall.data.entity.PdtDetailInf;
import com.bobooi.mall.data.repository.DataRepository;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
public interface PdtDetailViewRepository extends DataRepository<PdtDetailInf,Integer> {
    /**
     * 根据商品id获取商品详细信息
     *
     * @param productId 商品id
     * @return
     */
   PdtDetailInf findByProductId(Integer productId);
}
