package com.bobooi.mall.data.repository.concrete;

import com.bobooi.mall.data.entity.CartGoods;
import com.bobooi.mall.data.repository.DataRepository;

import java.util.List;

/**
 * @author bobo
 * @date 2021/8/4
 */

public interface CartGoodsRepository extends DataRepository<CartGoods, Integer> {
    List<CartGoods> findAllByCustomerId(Integer customerId);
}
