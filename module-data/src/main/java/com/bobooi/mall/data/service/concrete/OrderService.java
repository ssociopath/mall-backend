package com.bobooi.mall.data.service.concrete;


import com.bobooi.mall.common.exception.ApplicationException;
import com.bobooi.mall.common.exception.AssertUtils;
import com.bobooi.mall.common.response.SystemCodeEnum;
import com.bobooi.mall.data.config.redis.RedisUtil;
import com.bobooi.mall.data.dto.BatchOperationResultDTO;
import com.bobooi.mall.data.dto.OperationResultDTO;
import com.bobooi.mall.data.entity.CartGoods;
import com.bobooi.mall.data.entity.CsmInf;
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

    @PostConstruct
    public void initMap(){
        goodSecMap = new ConcurrentHashMap<>();
    }

    public boolean addOrder(double districtMoney, Integer productId,
                           Integer productAmount, Integer productTypeId, Integer customerAddrId){

        PdtInf pdtInf = pdtInfoRepository.findByProductId(productId);
        if(productAmount>pdtInf.getInventory()){
            log.info( "对应商品库存不足！");
            return false;
        }
        SimpleDateFormat orderSnFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");

        String orderAddr = userService.getUserAddrStr(customerAddrId);
        double orderMoney = pdtInf.getPrice()*productAmount;
        String productTypeName = pdtTypeRepository.getById(productTypeId).getProductTypeName();

        OrderMaster orderMaster = OrderMaster.builder()
                .orderSn(Long.valueOf(orderSnFormat.format((new Date()).getTime())))
                .customerId(userService.info().getCustomerId())
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
        pdtInf.setInventory(pdtInf.getInventory()-productAmount);
        pdtInfoRepository.save(pdtInf);
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
                    addOrder(districtMoney, cartGoods.getProductId(),
                            cartGoods.getProductAmount(), cartGoods.getProductTypeId(), customerAddrId);
                    cartGoodsService.deleteByCartGoodsId(cartGoodsId);
                    userService.updateUserPoint(userService.getUserPoint()-point);
                    return OperationResultDTO.success(cartGoodsId);
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
     public boolean secKill(Integer productId, Integer customerAddrId, Integer productTypeId) {
         if(null!=goodSecMap.get(productId)&&goodSecMap.get(productId)){
             RedisUtil.decrementKey("product" + productId);
             if(Integer.parseInt(String.valueOf(RedisUtil.getObject("product" + productId)))<0){
                 goodSecMap.put(productId,false);
                 RedisUtil.deleteKey("product" + productId);
                 if(pdtInfoRepository.findByProductId(productId)!=null){
                     pdtInfoRepository.deleteByProductId(productId);
                 }
                 log.info("商品销售完了");
                 return false;
             }
             addOrder(0,productId,1,productTypeId, customerAddrId);
             return true;
         }
         return false;
     }
}
