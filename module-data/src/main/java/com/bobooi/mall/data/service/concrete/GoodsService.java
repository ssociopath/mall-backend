package com.bobooi.mall.data.service.concrete;

import com.bobooi.mall.data.entity.PdtCategory;
import com.bobooi.mall.data.entity.PdtInf;
import com.bobooi.mall.data.entity.PdtDetailInf;
import com.bobooi.mall.data.repository.concrete.PdtCategoryRepository;
import com.bobooi.mall.data.repository.concrete.PdtDetailViewRepository;
import com.bobooi.mall.data.repository.concrete.PdtInfoRepository;
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
public class GoodsService extends BaseDataService<PdtInf,Integer> {
    @Resource
    PdtCategoryRepository pdtCategoryRepository;

    @Resource
    PdtInfoRepository pdtInfoRepository;

    @Resource
    PdtDetailViewRepository pdtDetailViewRepository;

    /**
     * 获取商品分类信息
     */
    public List<PdtCategory> getProductCategory(){
        return pdtCategoryRepository.findAll();
    }


    /**
     * 获取商品分类id为categoryId的所有商品信息
     *
     * @param categoryId 商品分类id
     * @return
     */
    public List<PdtInf> getAllPdtInfByCategoryId(Integer categoryId){
        return pdtInfoRepository.findAllByCategoryId(categoryId);
    }

    /**
     * 根据商品id获取商品详细信息
     *
     * @param productId 商品id
     * @return
     */
    public PdtDetailInf getPdtDetailInfoByPdtId(Integer productId){
        return pdtDetailViewRepository.findByProductId(productId);
    }
}
