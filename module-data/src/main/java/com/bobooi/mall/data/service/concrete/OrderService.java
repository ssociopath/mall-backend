package com.bobooi.mall.data.service.concrete;


import com.bobooi.mall.data.config.redis.RedisUtil;
import com.bobooi.mall.data.dto.BatchOperationResultDTO;
import com.bobooi.mall.data.dto.OperationResultDTO;
import com.bobooi.mall.data.entity.CartGoods;
import com.bobooi.mall.data.entity.OrderMaster;
import com.bobooi.mall.data.entity.PdtInf;
import com.bobooi.mall.data.repository.concrete.OrderMasterRepository;
import com.bobooi.mall.data.repository.concrete.PdtInfoRepository;
import com.bobooi.mall.data.repository.concrete.PdtTypeRepository;
import com.bobooi.mall.data.service.BaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
    PdtInfoRepository pdtInfoRepository;
    @Resource
    PdtTypeRepository pdtTypeRepository;
    @Resource
    CartGoodsService cartGoodsService;
    @Resource
    OrderMasterRepository orderMasterRepository;
    @Resource
    UserService userService;

    private ConcurrentHashMap<Integer,Boolean> goodSecMap;

    @Transactional
    public BatchOperationResultDTO<String> addOrder(Integer[] cartGoodsIds, Integer customerAddrId, Integer point){
        SimpleDateFormat orderSnFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
        double districtMoney = point/10.0/cartGoodsIds.length;

        return  Arrays.stream(cartGoodsIds)
                .map(cartGoodsId -> {
                    CartGoods cartGoods = cartGoodsService.getOne(cartGoodsId).orElse(null);
                    if(null==cartGoods){
                        return OperationResultDTO.fail(String.valueOf(cartGoodsId), "购物车商品不存在");
                    }
                    PdtInf pdtInf = pdtInfoRepository.findByProductId(cartGoods.getProductId());
                    if(cartGoods.getProductAmount()>pdtInf.getInventory()){
                        return OperationResultDTO.fail(String.valueOf(cartGoodsId), "对应商品库存不足！");
                    }
                    pdtInf.setInventory(pdtInf.getInventory()-cartGoods.getProductAmount());
                    pdtInfoRepository.save(pdtInf);

                    String orderAddr = "王小宝 12313123312 武汉理工大学北二食堂二楼烤鸭饭-招牌烤鸭饭";
                    double orderMoney = pdtInf.getPrice()*cartGoods.getProductAmount();
                    String productTypeName = pdtTypeRepository.getById(cartGoods.getProductTypeId()).getProductTypeName();

                    OrderMaster orderMaster = OrderMaster.builder()
                            .orderSn(Long.valueOf(orderSnFormat.format((new Date()).getTime())))
                            .customerId(userService.info().getCustomerId())
                            .orderAddr(orderAddr)
                            .picUrl(pdtInf.getPicUrl())
                            .productName(pdtInf.getProductName())
                            .productTypeName(productTypeName)
                            .productCnt(cartGoods.getProductAmount())
                            .districtMoney(districtMoney)
                            .orderMoney(orderMoney)
                            .payMoney(orderMoney-districtMoney)
                            .build();

                    this.insert(orderMaster);
                    cartGoodsService.deleteByCartGoodsId(cartGoodsId);
                    return OperationResultDTO.success(String.valueOf(cartGoodsId));
                }).collect(BatchOperationResultDTO.toBatchResult());
    }

    @PostConstruct
     public void initMap(){
         goodSecMap = new ConcurrentHashMap<>();
     }

     @Transactional
     public void initRedis(int goodId) {
         RedisUtil.setObject("good" + goodId, 20);
         goodSecMap.put(goodId,true);
     }

    public List<OrderMaster> findAllByCustomerId() {
        return orderMasterRepository.findAllByCustomerId(userService.info().getCustomerId());
    }

//    @Transactional
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
////             this.insert(new Order(null,goodId,1));
//             return true;
//         }
//         return false;
//     }
}
