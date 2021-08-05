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
// import com.bobooi.mall.data.config.redis.RedisUtil;
// import com.bobooi.mall.data.entity.Good;
// import com.bobooi.mall.data.entity.Order;
// import com.bobooi.mall.data.repository.concrete.GoodRepository;
// import com.bobooi.mall.data.service.BaseDataService;
// import lombok.SneakyThrows;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import javax.annotation.PostConstruct;
// import javax.annotation.Resource;
// import java.util.concurrent.ConcurrentHashMap;

// /**
//  * @author bobo
//  * @date 2021/8/3
//  */

// @Service
// @Slf4j
// public class OrderService extends BaseDataService<Order, Integer> {
//     @Resource
//     private GoodRepository goodRepository;

//     private ConcurrentHashMap<Integer,Boolean> goodSecMap;

//     @PostConstruct
//     public void initMap(){
//         goodSecMap = new ConcurrentHashMap<>();
//     }

//     @Transactional
//     public void initRedis(int goodId) {
//         RedisUtil.setObject("good" + goodId, 20);
//         goodSecMap.put(goodId,true);
//     }

//     @Transactional
//     public boolean secKill(int goodId) {
//         if(goodSecMap.get(goodId)){
//             RedisUtil.decrementKey("good" + goodId);
//             if(Integer.parseInt(String.valueOf(RedisUtil.getObject("good" + goodId)))<0){
//                 goodSecMap.put(goodId,false);
//                 RedisUtil.deleteKey("good" + goodId);
//                 goodRepository.updateStockByGoodId(goodId);
//                 log.info("商品销售完了");
//                 return false;
//             }
//             this.insert(new Order(null,goodId,1));
//             return true;
//         }
//         return false;
//     }
// }
