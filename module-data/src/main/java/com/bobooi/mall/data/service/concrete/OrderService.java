package com.bobooi.mall.data.service.concrete;

import com.bobooi.mall.data.entity.OrderMaster;
import com.bobooi.mall.data.repository.concrete.OrderMasterRepository;
import com.bobooi.mall.data.service.BaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
@Service
@Slf4j
public class OrderService extends BaseDataService<OrderMaster,Integer> {
    @Resource
    OrderMasterRepository orderMasterRepository;


    // AUTO
    public boolean generateOrderMaster(OrderMaster orderMaster){
       return null!=this.insert(orderMaster);
    }
}
