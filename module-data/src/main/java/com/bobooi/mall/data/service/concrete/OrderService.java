package com.bobooi.mall.data.service.concrete;


import com.bobooi.mall.common.exception.ApplicationException;
import com.bobooi.mall.common.exception.AssertUtils;
import com.bobooi.mall.common.response.SystemCodeEnum;
import com.bobooi.mall.common.utils.jackson.JsonUtils;
import com.bobooi.mall.data.bo.OrderBO;
import com.bobooi.mall.data.config.redis.RedisUtil;
import com.bobooi.mall.data.dto.BatchOperationResultDTO;
import com.bobooi.mall.data.dto.OperationResultDTO;
import com.bobooi.mall.data.entity.*;
import com.bobooi.mall.data.repository.concrete.*;
import com.bobooi.mall.data.service.BaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostConstruct
    public void initMap(){
        goodSecMap = new ConcurrentHashMap<>();
    }

    public boolean addOrder(double districtMoney, Integer productId,
                           Integer productAmount, Integer productTypeId,
                            Integer customerId, Integer customerAddrId){
        PdtInf pdtInf = pdtInfoRepository.findByProductId(productId);
        SimpleDateFormat orderSnFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");

        String orderAddr = userService.getUserAddrStr(customerAddrId, customerId);
        double orderMoney = pdtInf.getPrice()*productAmount;
        String productTypeName = pdtTypeRepository.findByProductTypeId(productTypeId).getProductTypeName();

        OrderMaster orderMaster = OrderMaster.builder()
                .orderSn(Long.valueOf(orderSnFormat.format((new Date()).getTime())))
                .customerId(customerId)
                .orderAddr(orderAddr)
                .picUrl(pdtInf.getPicUrl())
                .productName(pdtInf.getProductName())
                .productTypeName(productTypeName)
                .productCnt(productAmount)
                .districtMoney(districtMoney)
                .orderMoney(orderMoney)
                .payMoney(orderMoney-districtMoney)
                .payTime(new Timestamp(System.currentTimeMillis()))
                .build();

        this.insert(orderMaster);
        return true;
    }


    @Transactional
    public BatchOperationResultDTO<Integer> addOrders(Integer[] cartGoodsIds, Integer customerAddrId, Integer point){
        if(point>userService.getUserPoint()){
            return Arrays.stream(cartGoodsIds).map(cartGoodsId->
                    OperationResultDTO.fail(cartGoodsId, "用户积分不足！"))
                    .collect(BatchOperationResultDTO.toBatchResult());
        }
        double districtMoney = point/10.0/cartGoodsIds.length;

        return  Arrays.stream(cartGoodsIds)
                .map(cartGoodsId -> {
                    CartGoods cartGoods = cartGoodsService.getOne(cartGoodsId).orElse(null);
                    if(null==cartGoods){
                        return OperationResultDTO.fail(cartGoodsId, "购物车商品不存在！");
                    }
                    if(addOrder(districtMoney, cartGoods.getProductId(),
                            cartGoods.getProductAmount(), cartGoods.getProductTypeId(),
                            cartGoods.getCustomerId(), customerAddrId)){
                        cartGoodsService.deleteByCartGoodsId(cartGoodsId);
                        userService.updateUserPoint(userService.getUserPoint()-point);
                        return OperationResultDTO.success(cartGoodsId);
                    }
                    return OperationResultDTO.fail(cartGoodsId, "对应商品库存不足！");
                }).collect(BatchOperationResultDTO.toBatchResult());
    }

    public List<OrderMaster> findAllByCustomerId() {
        return orderMasterRepository.findAllByCustomerId(userService.info().getCustomerId());
    }

     public void initRedis(Integer productId) {
         RedisUtil.setObject("product" + productId, 20);
         goodSecMap.put(productId,true);
     }

    @Transactional
     public boolean secKill(Integer productId, Integer productTypeId, Integer customerId) {
         if(null!=goodSecMap.get(productId)&&goodSecMap.get(productId)){
             Long count = RedisUtil.decrementKey("product" + productId);
             if(count!=null&&count<0){
                 goodSecMap.put(productId,false);
                 RedisUtil.deleteKey("product" + productId);
                 if(pdtInfoRepository.findByProductId(productId)!=null){
                     pdtInfoRepository.deleteByProductId(productId);
                 }
                 log.info("商品销售完了");
                 return false;
             }
             addOrder(0,productId,1,productTypeId,customerId,
                     userService.getUserDefaultAddr(customerId).getCustomerAddrId());
             return true;
         }
         return false;
     }
}
