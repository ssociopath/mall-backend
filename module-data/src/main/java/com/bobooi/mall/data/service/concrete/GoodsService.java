package com.bobooi.mall.data.service.concrete;

import com.bobooi.mall.common.exception.ApplicationException;
import com.bobooi.mall.common.exception.AssertUtils;
import com.bobooi.mall.common.response.SystemCodeEnum;
import com.bobooi.mall.data.entity.PdtCategory;
import com.bobooi.mall.data.entity.PdtInf;
import com.bobooi.mall.data.entity.PdtDetailInf;
import com.bobooi.mall.data.entity.SupplierInf;
import com.bobooi.mall.data.repository.concrete.*;
import com.bobooi.mall.data.service.BaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
@Service
@Slf4j
public class GoodsService extends BaseDataService<PdtInf, Integer> {
    @Resource
    PdtCategoryRepository pdtCategoryRepository;

    @Resource
    PdtInfoRepository pdtInfoRepository;

    @Resource
    PdtAddiInfoRepository pdtAddiInfoRepository;

    @Resource
    PdtDetailViewRepository pdtDetailViewRepository;

    @Resource
    SupplierInfoRepository supplierInfoRepository;

    /**
     * 获取商品分类信息
     */
    public List<PdtCategory> getProductCategory() {
        return pdtCategoryRepository.findAll();
    }

    /**
     * 获取商品分类id为categoryId的所有商品信息
     *
     * @param categoryId 商品分类id
     * @return
     */
    public List<PdtInf> getAllPdtInfByCategoryId(Integer categoryId) {

        return pdtInfoRepository.findAllByCategoryId(categoryId);
    }

    /**
     * 根据商品id获取商品详细信息列表
     *
     * @param productId 商品id
     * @return
     */
    public List<PdtDetailInf> getPdtDetailInfoByPdtId(Integer productId) {
        List<PdtDetailInf> pdtDetailInfList = pdtDetailViewRepository.findByProductId(productId);
        AssertUtils.notNull(pdtDetailInfList,new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG,"该商品不存在或已经下架！"));
        return pdtDetailInfList;
    }

    /**
     * 获取所有商品详细信息返回给管理员
     *
     * @return
     */
    public List<PdtDetailInf> getAllPdtDetailInfo() {
        return pdtDetailViewRepository.findAll();
    }

    /**
     * 获取所有供应商信息给管理员
     *
     * @return
     */
    public List<SupplierInf> getAllSupplierInfo() {
        return supplierInfoRepository.findAll();
    }


    /**
     * 根据商品id删除商品所有信息
     *
     * @param productId 商品id
     * @return
     */
    public void deleteAllProductInfo(Integer productId) {
        pdtInfoRepository.deleteByProductId(productId);
        pdtAddiInfoRepository.deleteAllByProductId(productId);
    }

    /**
     * 更新商品信息
     *
     * @param productId
     * @param productName
     * @param description
     * @param picUrl
     * @param price
     * @param inventory
     * @return 返回是否成功（前端自己不要返回实体信息）
     */
    public boolean updateProductInfo(Integer productId, String productName, String description, String picUrl, Float price, Integer inventory){
        PdtInf pdtInf=pdtInfoRepository.getById(productId);
        System.out.println(pdtInf.getProductId());
        AssertUtils.notNull(pdtInf,new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG,"对应商品不存在！"));
        pdtInf.setProductName(productName);
        pdtInf.setDescription(description);
        pdtInf.setPicUrl(picUrl);
        pdtInf.setPrice(price);
        pdtInf.setInventory(inventory);
        return null!=pdtInfoRepository.save(pdtInf);
    }
}
