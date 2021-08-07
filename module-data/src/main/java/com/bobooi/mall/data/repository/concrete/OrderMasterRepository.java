package com.bobooi.mall.data.repository.concrete;

import com.bobooi.mall.data.entity.OrderMaster;
import com.bobooi.mall.data.repository.DataRepository;

import java.util.List;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
public interface OrderMasterRepository extends DataRepository<OrderMaster,Integer> {
    List<OrderMaster> findAllByCustomerId(Integer customerId);
}
