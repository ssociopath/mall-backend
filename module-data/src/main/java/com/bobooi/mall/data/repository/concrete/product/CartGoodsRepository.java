package com.bobooi.mall.data.repository.concrete.product;

import com.bobooi.mall.data.entity.product.CartGoods;
import com.bobooi.mall.data.repository.DataRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author bobo
 * @date 2021/8/4
 */

public interface CartGoodsRepository extends DataRepository<CartGoods, Integer> {
    List<CartGoods> findAllByCustomerId(Integer customerId);

    @Transactional
    @Modifying
    void deleteByCartGoodsId(Integer cartGoodsId);

    @Transactional
    @Modifying
    void deleteAllByCustomerId(Integer customerId);
    CartGoods findByCustomerIdAndProductIdAndProductTypeId(Integer customerId, Integer productId, Integer productTypeId);
}
