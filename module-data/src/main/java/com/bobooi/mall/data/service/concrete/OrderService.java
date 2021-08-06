package com.bobooi.mall.data.service.concrete;


import com.bobooi.mall.data.config.redis.RedisUtil;
import com.bobooi.mall.data.entity.OrderMaster;
import com.bobooi.mall.data.repository.concrete.GoodRepository;
import com.bobooi.mall.data.repository.concrete.OrderMasterRepository;
import com.bobooi.mall.data.service.BaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
    @Resource
    GoodRepository goodRepository;

    private ConcurrentHashMap<Integer,Boolean> goodSecMap;

    @PostConstruct
     public void initMap(){
         goodSecMap = new ConcurrentHashMap<>();
     }

     @Transactional
     public void initRedis(int goodId) {
         RedisUtil.setObject("good" + goodId, 20);
         goodSecMap.put(goodId,true);
     }

    @Transactional
     public boolean secKill(int goodId) {
         if(goodSecMap.get(goodId)){
             RedisUtil.decrementKey("good" + goodId);
             if(Integer.parseInt(String.valueOf(RedisUtil.getObject("good" + goodId)))<0){
                 goodSecMap.put(goodId,false);
                 RedisUtil.deleteKey("good" + goodId);
                 goodRepository.updateStockByGoodId(goodId);
                 log.info("商品销售完了");
                 return false;
             }
//             this.insert(new Order(null,goodId,1));
             return true;
         }
         return false;
     }
}
